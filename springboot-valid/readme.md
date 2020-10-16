参考链接： [注解校验验证](https://www.cnblogs.com/lalalazar/p/12380699.html)

- 基本注解使用
- 自定义全局异常捕捉校验信息
- 嵌套类校验
- 定义分组校验
- 定义枚举校验 @Constraint注解
 
## 1、校验注解比较
```
@Valid是使用hibernate validation的时候使用；
@Validated 是只用spring  Validator 校验机制使用；
@valid，java的jsr303声明了这类接口，hibernate-validator对其进行了实现
```

## 2、空检查
```
@Null       验证对象是否为null

@NotNull    验证对象是否不为null, 无法查检长度为0的字符串

@NotBlank   检查约束字符串是不是Null还有被Trim的长度是否大于0,只对字符串,且会去掉前后空格.

@NotEmpty   检查约束元素是否为NULL或者是EMPTY.

```

## 3、Booelan检查
```
@AssertTrue     验证 Boolean 对象是否为 true  

@AssertFalse    验证 Boolean 对象是否为 false  
```

## 4、长度检查

@Size(min=, max=) 验证对象（Array,Collection,Map,String）长度是否在给定的范围之内  

@Length(min=, max=) Validates that the annotated string is between min and max included.

1、自定义注解不能和@NotBlank或者@NotNull配合使用，自定义注解中如果需要判断null需要在注解中自行定义属性控制