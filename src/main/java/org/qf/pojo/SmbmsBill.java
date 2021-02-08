package org.qf.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "smbms_bill")
public class SmbmsBill {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    @Column(name = "billCode")
    private String billcode;

    @Column(name = "productName")
    private String productname;

    @Column(name = "productDesc")
    private String productdesc;

    @Column(name = "productUnit")
    private String productunit;

    @Column(name = "productCount")
    private BigDecimal productcount;

    @Column(name = "totalPrice")
    private BigDecimal totalprice;

    @Column(name = "isPayment")
    private Integer ispayment;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "creationDate")
    private Date creationdate;

    @Column(name = "providerId")
    private Long providerid;

    @Transient
    private String providerName;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillcode() {
        return billcode;
    }

    public void setBillcode(String billcode) {
        this.billcode = billcode == null ? null : billcode.trim();
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname == null ? null : productname.trim();
    }

    public String getProductdesc() {
        return productdesc;
    }

    public void setProductdesc(String productdesc) {
        this.productdesc = productdesc == null ? null : productdesc.trim();
    }

    public String getProductunit() {
        return productunit;
    }

    public void setProductunit(String productunit) {
        this.productunit = productunit == null ? null : productunit.trim();
    }

    public BigDecimal getProductcount() {
        return productcount;
    }

    public void setProductcount(BigDecimal productcount) {
        this.productcount = productcount;
    }

    public BigDecimal getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(BigDecimal totalprice) {
        this.totalprice = totalprice;
    }

    public Integer getIspayment() {
        return ispayment;
    }

    public void setIspayment(Integer ispayment) {
        this.ispayment = ispayment;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public Long getProviderid() {
        return providerid;
    }

    public void setProviderid(Long providerid) {
        this.providerid = providerid;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
}