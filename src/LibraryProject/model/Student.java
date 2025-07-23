package LibraryProject.model;

import LibraryProject.util.MemberType;

public class Student extends Reader{

    public static final int DEFAULT_LIMIT = 5;

    public Student(String memberId, String name, String address, String phone) {
        super(name, new MemberRecord(memberId, MemberType.STUDENT, DEFAULT_LIMIT, name, address, phone));
    }

    @Override
    public String whoYouAre() {
        return "Student: " + getName();
    }
}
