package it.unisa.fhirconnection.fhirStarter.RestController;

import lombok.NoArgsConstructor;


public class PatientForm {
    private int id;
    private String firstname;
    private String familyname;
    private String gender;
    private String date;
    private String telecomValue;
    private String telecomUse;
    private String addressLine;
    private String city;
    private String postCode;
    private String country;
    private String addressUse;
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFamilyname() {
        return familyname;
    }

    public void setFamilyname(String familyname) {
        this.familyname = familyname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTelecomValue() {
        return telecomValue;
    }

    public void setTelecomValue(String telecomValue) {
        this.telecomValue = telecomValue;
    }

    public String getTelecomUse() {
        return telecomUse;
    }

    public void setTelecomUse(String telecomUse) {
        this.telecomUse = telecomUse;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddressUse() {
        return addressUse;
    }

    public void setAddressUse(String addressUse) {
        this.addressUse = addressUse;
    }

    @Override
    public String toString() {
        return "DummyPatient{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", familyname='" + familyname + '\'' +
                ", gender='" + gender + '\'' +
                ", date='" + date + '\'' +
                ", telecomValue='" + telecomValue + '\'' +
                ", telecomUse='" + telecomUse + '\'' +
                ", addressLine='" + addressLine + '\'' +
                ", city='" + city + '\'' +
                ", postCode='" + postCode + '\'' +
                ", country='" + country + '\'' +
                ", addressUse='" + addressUse + '\'' +
                '}';
    }
}
