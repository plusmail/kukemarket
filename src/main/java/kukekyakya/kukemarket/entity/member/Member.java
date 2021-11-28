package kukekyakya.kukemarket.entity.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String email;

    private String password;

    @Column(nullable = false, length = 20)
    private String username;

    @Column(nullable = false, unique = true, length = 20)
    private String nickname;

    @OneToMany(mappedBy = "member")
    private List<MemberRole> roles;

    public Member(String email, String password, String username, String nickname, List<RoleType> roleTypes) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
        this.roles = roleTypes.stream().map(r -> new MemberRole(this, new Role(r))).collect(toList());
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isMatch(String password) {
        return Objects.equals(this.password, password);
    }
}
