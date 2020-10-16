package com.demo.valid.entity;

import com.demo.valid.annotation.Insert;
import com.demo.valid.annotation.Update;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Data
public class Goods {

    @Size(message = "id长度不能超过5", groups = {Update.class}, min = 1, max =5)
    @NotBlank(message = "id不能为空", groups = {Update.class})
    private String id;

    @Size(message = "货物名称长度不能超过5", groups = {Insert.class, Update.class}, min = 1, max =5)
    @NotBlank(message = "货物名称不能为空", groups = {Insert.class,Update.class})
    private String goodsName;

    @Length(message = "发货人姓名长度不能超过3", min = 1, max = 3)
    @NotBlank(message = "发货人不能为空")
    private String sendName;

    @NotNull(message = "装货数量不能为空")
    private Integer quality;

    @NotNull(message = "货物金额不能为空")
    private BigDecimal goodsPrice;

    @NotNull(message = "运输距离不能为空")
    private Double distance;

    @NotNull(message = "是否发布到货源大厅不能为空")
    @AssertTrue(message = "是否发布到货源大厅有误")
    private Boolean isPublic;

    @Null(message = "错误数据")
    @CreditCardNumber
    private String error;

    @Valid
    @NotEmpty(message = "货主信息不能为空")
    private List<Shipper> shipperList;

}
