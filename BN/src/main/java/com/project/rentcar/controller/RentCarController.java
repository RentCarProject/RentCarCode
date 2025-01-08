package com.project.rentcar.controller;

import com.project.rentcar.config.DataSourceConfig;
import com.project.rentcar.domain.entity.Car;
import com.project.rentcar.domain.service.CarService;
import com.zaxxer.hikari.HikariDataSource;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class RentCarController {
    private static final Logger log = LoggerFactory.getLogger(RentCarController.class);
    private WebDriver driver;

    @Autowired
    private DataSourceConfig dataSourceConfig;

    @Autowired
    private HikariDataSource dataSource;

    @Autowired
    private CarService carService;

    @GetMapping("/open/rentcar")
    public List<Car> rentcar() throws InterruptedException {
        log.info("GET /open/rentcar");

        List<Car> carList = new ArrayList<>();

        // WebDriver 설정
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless"); // 필요 시 헤드리스 모드
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");

        driver = new ChromeDriver(options);

        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.get("https://homepage.skcarrental.com/");

            // 광고창 닫기
            WebElement adCloseButton = driver.findElement(By.cssSelector(".btn_close"));
            adCloseButton.click();

            // 차량 목록 페이지로 이동
            WebElement carListButton = driver.findElement(By.cssSelector(".btn.type01"));
            carListButton.click();

            Thread.sleep(5000); // 페이지 로드 대기 (필요 시 WebDriverWait로 대체)

            // 차량 목록 크롤링
            List<WebElement> carElements = driver.findElements(By.cssSelector(".container .dill_box_type"));
            log.info("총 차량 개수: " + carElements.size());

            for (int i = 0; i < carElements.size(); i++) {
                try {
                    WebElement carElement = carElements.get(i);
                    String details = carElement.getText();  // 차량 정보 (상세 내용)

                    // 세부 페이지 진입
                    WebElement detailButton = carElement.findElement(By.cssSelector(".list_item"));
                    detailButton.click();

                    Thread.sleep(5000); // 상세 페이지 로드 대기

                    Car car = Car.builder()
                            .carName(getCarName())
                            .price(getPrice())
                            .fuel(getFuel())
                            .seatingCapacity(getSeatingCapacity())
                            .year(getYear())
                            .driverAgeRequirement(getDriverAgeRequirement())
                            .drivingExperience(getDrivingExperience())
                            .manufacturer(getManufacturer())
                            .option1(getOption1())
                            // === 이미지 URL 추가 ===
                            .imageUrl(getImageUrl())
                            .carLogo(getCarLogo())
                            .build();

                    carList.add(carService.saveCar(car));

                    log.info("차량 세부 정보: " + details);

                    // 목록 페이지로 돌아가기
                    driver.get("https://homepage.skcarrental.com/carList");
                    Thread.sleep(5000);

                    // 다시 carElements 재참조
                    carElements = driver.findElements(By.cssSelector(".container .dill_box_type"));
                } catch (Exception e) {
                    log.error("크롤링 중 예외 발생: " + e.getMessage(), e);
                }
            }

        } finally {
            driver.quit(); // 브라우저 종료
        }
        return carList;
    }


    // ------------------------------
    // 아래부터는 각종 추출 메서드
    // ------------------------------

    private String getCarName() {
        return getElementText(".brand_info strong");
    }

    public String getManufacturer() {
        try {
            WebElement h2Element = driver.findElement(By.cssSelector(".brand_info h2"));
            String fullText = h2Element.getText();
            WebElement strongElement = h2Element.findElement(By.tagName("strong"));
            String strongText = strongElement.getText();
            String result = fullText.replace(strongText, "").trim();
            return result;
        } catch (NoSuchElementException e) {
            log.error("요소를 찾을 수 없습니다: .brand_info h2", e);
            return null;
        }
    }

    private BigDecimal getPrice() {
        String priceText = getElementText(".rental_price .price");
        try {
            String cleanedPriceText = priceText.replace(",", "").replace("원", "").trim();
            return new BigDecimal(cleanedPriceText);
        } catch (NumberFormatException e) {
            log.error("가격 정보 형식 오류: " + priceText, e);
            return BigDecimal.ZERO;
        }
    }

    private int getSeatingCapacity() {
        String text = getElementText(".spec_list li:nth-child(2) .obj");
        try {
            return Integer.parseInt(text.replace("명", "").trim());
        } catch (NumberFormatException e) {
            log.error("승차 인원 정보 형식 오류: " + text, e);
            return 0;
        }
    }

    private String getYear() {
        return getElementText(".spec_list li:nth-child(3) .obj");
    }

    private String getDriverAgeRequirement() {
        return getElementText(".spec_list li:nth-child(4) .obj");
    }

    private String getDrivingExperience() {
        return getElementText(".spec_list li:nth-child(5) .obj");
    }

    private String getFuel() {
        return getElementText(".spec_list li:nth-child(1) .obj");
    }

    private String getModel() {
        return getElementText(".car_name .sb_text");
    }

    private String getOption1() {
        return getElementText(".option_txt p");
    }

    // === 핵심: 이미지 URL 가져오는 메서드 ===
    private String getImageUrl() {
        try {
            // 예: 상세 페이지에서 .img_area > img 태그를 찾는다고 가정
            // 실제 사이트 구조를 보고 selector를 바꾸세요.
            WebElement imgElement = driver.findElement(By.cssSelector(".img_area img"));
            String src = imgElement.getAttribute("src");
            return (src != null) ? src.trim() : "";
        } catch (NoSuchElementException e) {
            log.error("이미지 요소를 찾지 못했습니다: .img_area img", e);
            return "";
        }
    }

    private String getCarLogo() {
        try {
            // 예: 상세 페이지에서 .img_area > img 태그를 찾는다고 가정
            // 실제 사이트 구조를 보고 selector를 바꾸세요.
            WebElement imgElement = driver.findElement(By.cssSelector(".car_logo img"));
            String src = imgElement.getAttribute("src");
            return (src != null) ? src.trim() : "";
        } catch (NoSuchElementException e) {
            log.error("이미지 요소를 찾지 못했습니다: .img_area img", e);
            return "";
        }
    }

    // 공용: 텍스트 추출 메서드
    private String getElementText(String cssSelector) {
        try {
            WebElement element = driver.findElement(By.cssSelector(cssSelector));
            return (element != null) ? element.getText().trim() : "";
        } catch (Exception e) {
            log.error("요소를 찾는 중 오류 발생: " + cssSelector, e);
            return "";
        }
    }
}
