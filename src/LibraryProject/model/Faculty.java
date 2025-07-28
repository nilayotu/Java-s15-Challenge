package LibraryProject.model;

import LibraryProject.util.MemberType;

public class Faculty extends Reader{

    public static final int DEFAULT_LIMIT = 5;


    public Faculty(String memberId, String name, String address, String phone) {
        super(name, new MemberRecord(memberId, MemberType.FACULTY, DEFAULT_LIMIT, name, address, phone));
    }

    @Override
    public String whoYouAre() {
        return "Faculty: " + getName();
    }
}
