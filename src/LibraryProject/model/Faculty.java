package LibraryProject.model;

import LibraryProject.util.MemberType;

public class Faculty extends Reader{

    public Faculty(String memberId, String name, String address, String phone) {
        super(name, new MemberRecord(memberId, MemberType.FACULTY, 0, name, address, phone));
    }

    @Override
    public String whoYouAre() {
        return "Faculty: " + getName();
    }
}
