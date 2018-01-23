package com.scut.originsystem.entity;

import javax.validation.Valid;

public class RegisterMerchantInfo {
    @Valid
    private User user;
    @Valid
    private Merchant merchant;
    @Valid
    private Company company;

    public RegisterMerchantInfo(){
        this.user=null;
        this.merchant=null;
        this.company=null;
    }

    public RegisterMerchantInfo(User user,Merchant merchant,Company company){
        this.user = user;
        this.merchant = merchant;
        this.company = company;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    @Override
    public String toString() {
        return "RegisterMerchantInfo{" +
                "user=" + user +
                ", merchant=" + merchant +
                ", company=" + company +
                '}';
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
