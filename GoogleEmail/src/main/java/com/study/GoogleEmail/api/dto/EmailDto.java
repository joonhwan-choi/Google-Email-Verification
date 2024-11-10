package com.study.GoogleEmail.api.dto;

public class EmailDto {

    private String mail;
    private String verifyCode;

    // Getter와 Setter
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
