package LibraryProject.model;

import LibraryProject.util.MemberType;

import java.time.LocalDate;

public class MemberRecord {

    private String memberId;
    private MemberType type;
    private LocalDate dateOfMembership;
    private int noBooksIssued;
    private int maxBookLimit;
    private String name;
    private String address;
    private String phoneNumber;
    private double balance;

    public MemberRecord(String memberId,
                        MemberType type,
                        int maxBookLimit,
                        String name,
                        String address,
                        String phoneNumber) {
        this.memberId = memberId;
        this.type = type;
        this.maxBookLimit = maxBookLimit;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.dateOfMembership = LocalDate.now();
        this.noBooksIssued = 0;
        this.balance = 0.0;
    }

    public String getMember() {
        return memberId;
    }

    public MemberType getType() {
        return type;
    }

    public LocalDate getDateOfMembership() {
        return dateOfMembership;
    }

    public int getNoBooksIssued() {
        return noBooksIssued;
    }

    public int getMaxBookLimit() {
        return maxBookLimit;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getBalance() {
        return balance;
    }

    public boolean canBorrow() {
        return noBooksIssued < maxBookLimit;
    }

    public void incBookIssued() {
        noBooksIssued++;
    }

    public void decBookIssued() {
        if (noBooksIssued > 0) noBooksIssued--;
    }

    public void payBill(double amount) {
        balance -= amount; // NEGATIF DE GELEBILIR KONTROL EDILMELI
    }

    @Override
    public String toString() {
        return "MemberRecord{" +
                "memberId='" + memberId + '\'' +
                ", type=" + type +
                ", noBooksIssued=" + noBooksIssued +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }


}
