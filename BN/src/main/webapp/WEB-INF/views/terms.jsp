<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>동의 항목</title>
 <script>
        // 폼 제출 전에 체크박스를 모두 선택했는지 확인하는 함수
        function validateTermsForm() {
            // 모든 체크박스를 가져옴
            const checkboxes = document.querySelectorAll('input[name="termIds"]');

            // 체크된 체크박스가 7개인지 확인
            let checkedCount = 0;
            checkboxes.forEach(function(checkbox) {
                if (checkbox.checked) {
                    checkedCount++;
                }
            });

            // 7개 모두 체크되어 있지 않으면 경고 메시지 출력 후 폼 제출을 막음
            if (checkedCount !== checkboxes.length) {
                alert('이용약관에 동의하셔야 회원가입을 하실 수 있습니다.\n동의에 체크해 주시기 바랍니다');
                return false;  // 폼 제출을 막음
            }
            return true;  // 폼을 제출하도록 허용
        }
    </script>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f7fc;
            margin: 0;
            padding: 0;
        }

        header {
            background-color: #4CAF50;
            color: white;
            text-align: center;
            padding: 20px;
        }

        h1 {
            font-size: 32px;
            margin-bottom: 20px;
        }

        .container {
            width: 80%;
            margin: 30px auto;
            padding: 20px;
            background-color: white;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }

        .term-list {
            list-style-type: none;
            padding-left: 0;
        }

        .term-list li {
            margin-bottom: 20px;
            padding: 15px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background-color: #f9f9f9;
            transition: background-color 0.3s ease;
        }

        .term-list li:hover {
            background-color: #f0f8ff;
        }

        label {
            display: block;
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 10px;
        }

        input[type="checkbox"] {
            margin-right: 10px;
        }

        span {
            font-size: 20px;
            color: #333;
        }

        p {
            font-size: 14px;
            color: #555;
            line-height: 1.6;
            margin: 10px 0;
        }

        button[type="submit"] {
            width: 100%;
            padding: 15px;
            background-color: #4CAF50;
            color: white;
            font-size: 18px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button[type="submit"]:hover {
            background-color: #45a049;
        }

        .footer {
            text-align: center;
            margin-top: 40px;
            font-size: 14px;
            color: #888;
        }
    </style>
</head>
<body>

<header>
    <h1>이용 약관에 동의해주세요</h1>
</header>

<div class="container">
    <form action="/terms/agree" method="post" onsubmit="return validateTermsForm()">
        <ul class="term-list">
            <!-- 이용 약관 동의 항목 -->
            <li>
                <label>
                    <input type="checkbox" name="termIds" value="1" />
                    <span>이용 약관</span>
                    <p>본 이용 약관은 귀하가 차량을 예약하고 이용하는 모든 서비스에 대해 적용됩니다. 본 약관을 숙지하고 동의한 후, 렌터카 서비스를 이용하시기 바랍니다.</p>
                    <p>1. 서비스 이용 규정: 본 회사의 차량을 예약하고 이용하는 모든 고객은 본 약관을 준수해야 합니다.</p>
                    <p>2. 계약의 체결: 차량 예약은 본 시스템을 통해 이루어지며, 예약과 동시에 본 약관에 동의한 것으로 간주됩니다.</p>
                    <p>3. 예약 취소 및 환불: 예약 취소 시, 회사 정책에 따른 환불 규정이 적용됩니다. 취소 시 일정 수수료가 부과될 수 있습니다.</p>
                    <p>4. 고객의 책임: 고객은 차량의 손상, 사고, 교통법규 위반 등에 대해 전적으로 책임을 지며, 렌터카 회사에 이를 알리고 필요한 절차를 따라야 합니다.</p>
                </label>
            </li>

            <!-- 개인정보 처리방침 동의 항목 -->
            <li>
                <label>
                    <input type="checkbox" name="termIds" value="2" />
                    <span>개인정보 처리방침</span>
                    <p>당사는 고객님의 개인정보 보호를 최우선으로 합니다. 개인정보 처리방침을 확인하고 동의해 주시기 바랍니다.</p>
                    <p>1. 개인정보 수집 항목: 이름, 연락처, 이메일, 운전면허 정보, 차량 예약 내역 등</p>
                    <p>2. 개인정보의 이용 목적: 서비스 제공, 예약 확인, 차량 대여 및 반환 관리, 고객 문의 응답 등을 위해 사용됩니다.</p>
                    <p>3. 개인정보의 보유 및 이용 기간: 고객님의 개인정보는 서비스 제공 기간 동안 보유되며, 이후 법적 요구사항에 따라 일정 기간 보관됩니다.</p>
                    <p>4. 제3자 제공: 고객님의 동의 없이 개인정보를 제3자에게 제공하지 않습니다.</p>
                </label>
            </li>

            <!-- 렌터카 이용 규정 동의 항목 -->
            <li>
                <label>
                    <input type="checkbox" name="termIds" value="3" />
                    <span>렌터카 이용 규정</span>
                    <p>차량 대여 및 이용에 대한 규정을 안내드립니다. 이를 숙지하시고 동의해 주시기 바랍니다.</p>
                    <p>1. 차량 사용 가능 시간: 차량은 예약된 시간에만 사용 가능하며, 반환 시간에 지연이 발생할 경우 추가 비용이 발생할 수 있습니다.</p>
                    <p>2. 차량 반환: 차량은 대여한 상태 그대로 반환해야 하며, 차량에 손상이나 오염이 있을 경우 추가 요금이 부과될 수 있습니다.</p>
                    <p>3. 차량 운전: 차량 운전자는 유효한 운전면허증을 보유해야 하며, 면허 취득 후 최소 1년 이상 경과해야 합니다.</p>
                    <p>4. 연료 및 상태: 차량은 렌터카 회사에서 제공하는 연료 수준으로 반환해야 하며, 연료 부족 시 추가 요금이 부과될 수 있습니다.</p>
                </label>
            </li>

            <!-- 보험 관련 사항 동의 항목 -->
            <li>
                <label>
                    <input type="checkbox" name="termIds" value="4" />
                    <span>보험 관련 사항</span>
                    <p>렌터카 차량에는 기본 보험이 포함되어 있으나, 추가 보험 옵션에 대한 선택을 권장합니다.</p>
                    <p>1. 기본 보험: 렌터카 대여 시 기본적으로 제공되는 보험은 사고 발생 시 일정 부분의 책임을 면제해 주나, 자차보험 등 추가 보험을 가입하지 않은 경우 일부 비용은 고객이 부담해야 합니다.</p>
                    <p>2. 추가 보험: 고객은 추가 보험(자차보험, 책임보험 등)을 선택할 수 있으며, 이 경우 사고 발생 시 더 넓은 범위의 보장을 받을 수 있습니다.</p>
                    <p>3. 보험 면책 사항: 음주 운전, 불법 주정차, 고의적인 사고 등은 보험의 적용을 받지 않습니다. 이러한 경우 고객이 전액 부담해야 합니다.</p>
                </label>
            </li>

            <!-- 음주 및 약물 관련 동의 항목 -->
            <li>
                <label>
                    <input type="checkbox" name="termIds" value="5" />
                    <span>음주 및 약물 관련</span>
                    <p>음주나 약물을 복용한 상태에서 차량을 운전하는 것은 법적으로 금지되어 있으며, 이를 위반할 경우 사고 발생 시 법적 책임이 따를 수 있습니다.</p>
                    <p>1. 음주 운전 금지: 차량 대여 시 음주 측정을 요구할 수 있으며, 음주 측정에서 음성 반응이 나올 경우 차량 대여가 불가능합니다.</p>
                    <p>2. 약물 복용 시 운전 금지: 처방전 없이 약물을 복용한 상태에서 운전하는 것은 금지됩니다. 이는 사고 발생 시 보험 적용이 되지 않을 수 있습니다.</p>
                </label>
            </li>

            <!-- 차량 상태 확인 및 반환 규정 동의 항목 -->
            <li>
                <label>
                    <input type="checkbox" name="termIds" value="6" />
                    <span>차량 상태 확인 및 반환 규정</span>
                    <p>차량을 대여받을 때 차량 상태를 반드시 확인하시고, 반환 시 차량 상태가 원상복구되어야 합니다.</p>
                    <p>1. 차량 상태 점검: 대여 시 차량에 이상이 없는지 점검하고, 차량 상태를 기록합니다. 반환 시 차량에 손상이 있으면 추가 비용이 청구될 수 있습니다.</p>
                    <p>2. 반환 시간: 차량은 예약한 시간 내에 반드시 반환해야 하며, 반환 지연 시 추가 비용이 발생합니다.</p>
                </label>
            </li>

            <!-- 기타 규정 및 법적 책임 동의 항목 -->
            <li>
                <label>
                    <input type="checkbox" name="termIds" value="7" />
                    <span>기타 규정 및 법적 책임</span>
                    <p>렌터카 서비스를 이용하는 동안 발생하는 모든 법적 책임은 고객이 부담하게 됩니다. 아래의 규정들을 숙지하시기 바랍니다.</p>
                    <p>1. 차량 파손 및 사고 발생 시, 렌터카 회사는 일정 부분의 책임을 지며, 나머지 부분에 대해서는 고객이 전적으로 책임을 집니다.</p>
                    <p>2. 불법 주정차, 교통법규 위반으로 발생한 벌금이나 사고에 대해서는 고객이 전적으로 책임을 집니다.</p>
                    <p>3. 본 규정은 법적 효력이 있으며, 규정을 위반할 경우 법적 절차에 따라 처벌을 받을 수 있습니다.</p>
                </label>
            </li>
        </ul>

        <button type="submit">동의하고 회원가입</button>
    </form>
</div>

<div class="footer">
    <p>© 2024 RentCar. 모든 권리 보유.</p>
</div>

</body>
</html>
