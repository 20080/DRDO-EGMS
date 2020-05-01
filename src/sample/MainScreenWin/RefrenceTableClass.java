package sample.MainScreenWin;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Suraj on 5/27/2017.
 */
public class RefrenceTableClass {

    IntegerProperty id = new SimpleIntegerProperty(0);
    StringProperty name = new SimpleStringProperty("");
    StringProperty address = new SimpleStringProperty("");
    StringProperty purpose = new SimpleStringProperty("");
    StringProperty sitting_place = new SimpleStringProperty("");
    StringProperty date = new SimpleStringProperty("");
    StringProperty company = new SimpleStringProperty("");
    StringProperty phone = new SimpleStringProperty("");
    StringProperty session = new SimpleStringProperty("");

    public RefrenceTableClass(int id, String name, String company, String address,
                              String purpose, String date, String phone, String sitting_place, String session) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.purpose = new SimpleStringProperty(purpose);
        this.sitting_place = new SimpleStringProperty(sitting_place);
        this.date = new SimpleStringProperty(date);
        this.company = new SimpleStringProperty(company);
        this.phone = new SimpleStringProperty(phone);
        this.session = new SimpleStringProperty(session);


    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setPurpose(String purpose) {
        this.purpose.set(purpose);
    }

    public void setSitting_place(String sitting_place) {
        this.sitting_place.set(sitting_place);
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public void setCompany(String company) {
        this.company.set(company);
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public void setSession(String session) {
        this.session.set(session);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getPurpose() {
        return purpose.get();
    }

    public StringProperty purposeProperty() {
        return purpose;
    }

    public String getSitting_place() {
        return sitting_place.get();
    }

    public StringProperty sitting_placeProperty() {
        return sitting_place;
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public String getCompany() {
        return company.get();
    }

    public StringProperty companyProperty() {
        return company;
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public String getSession() {
        return session.get();
    }

    public StringProperty sessionProperty() {
        return session;
    }
}

