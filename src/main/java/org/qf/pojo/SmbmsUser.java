package org.qf.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Calendar;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
@Table(name = "smbms_user")
public class SmbmsUser {

    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name = "id")
    private Long id;

    @Column(name = "userCode")
    private String usercode;

    @Column(name = "userName")
    private String username;

    @Column(name = "userPassword")
    private String userpassword;

    @Transient
    private Integer age;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "birthday")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "userRole")
    private Long userrole;

    @Column(name = "creationDate")
    private Date creationdate;

    @Transient
    private String roleName;

    public Long getId() {
        return id;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode == null ? null : usercode.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword == null ? null : userpassword.trim();
    }

    public Integer getAge() {
        Calendar nowDate = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();
        birth.setTime(this.getBirthday());
        return nowDate.get(Calendar.YEAR)-birth.get(Calendar.YEAR);
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Long getUserrole() {
        return userrole;
    }

    public void setUserrole(Long userrole) {
        this.userrole = userrole;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }
}