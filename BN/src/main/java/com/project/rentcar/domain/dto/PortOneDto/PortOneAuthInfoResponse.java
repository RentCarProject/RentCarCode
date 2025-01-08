package com.project.rentcar.domain.dto.PortOneDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PortOneAuthInfoResponse {
    @JsonProperty("birth")
    private long birth; // UNIX 타임스탬프 형식

    @JsonProperty("birthday")
    private String birthday; // "1996-12-14" 형식

    @JsonProperty("certified")
    private boolean certified;

    @JsonProperty("certified_at")
    private long certifiedAt; // UNIX 타임스탬프 형식

    @JsonProperty("foreigner")
    private boolean foreigner;

    @JsonProperty("foreigner_v2")
    private boolean foreignerV2;

    @JsonProperty("gender")
    private String gender; // "male" 또는 "female"

    @JsonProperty("imp_uid")
    private String impUid;

    @JsonProperty("merchant_uid")
    private String merchantUid;

    @JsonProperty("name")
    private String name;

    @JsonProperty("origin")
    private String origin;

    @JsonProperty("pg_provider")
    private String pgProvider;

    @JsonProperty("pg_tid")
    private String pgTid;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("unique_in_site")
    private String uniqueInSite;

    @JsonProperty("unique_key")
    private String uniqueKey;
}
