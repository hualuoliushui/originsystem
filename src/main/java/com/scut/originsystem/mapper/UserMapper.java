package com.scut.originsystem.mapper;

import com.scut.originsystem.entity.User;
import com.scut.originsystem.returnentity.AreaPCD;
import com.scut.originsystem.returnentity.MerchantUser;
import com.scut.originsystem.util.SqlUtil;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "userMapper")

public interface UserMapper {
    //查找user
    @Select("select * from user_t where username = #{username} and activation_code<>4 limit 1")
    User findUser(@Param("username") String username);

    //查找user
    @Select("select * from user_t where id=#{id} and activation_code<>4")
    User findUserById(@Param("id") int id);

    String sql_findUsersByUserName = " from user_t where username like concat('%',#{user_name},'%') and activation_code<>4 ";
    //通过user_name查找用户
    @Select("select * " + sql_findUsersByUserName)
    List<User> findUsersByUserName(@Param("user_name")String user_name);

    @Select("select * " + sql_findUsersByUserName + SqlUtil.PAGE_LIMIT_SQL)
    List<User> findUsersByUserNameWithPage(@Param("user_name")String user_name,
                                   @Param("limit") int limit,
                                   @Param("offset") int offset);

    @Select(SqlUtil.COUNT_SQL + sql_findUsersByUserName)
    int getTotal_findUsersByUserName(@Param("user_name")String user_name);

    String sql_findUsersByUserNameExceptMerchant = " from user_t " +
            "where username like concat('%',#{user_name},'%') " +
            "and activation_code<>4 " +
            "and role<>'MERCHANT' ";
    //通过user_name查找用户（排除商户）
    @Select("select * " + sql_findUsersByUserNameExceptMerchant)
    List<User> findUsersByUserNameExceptMerchant(@Param("user_name")String user_name);

    @Select("select * " + sql_findUsersByUserNameExceptMerchant + SqlUtil.PAGE_LIMIT_SQL)
    List<User> findUsersByUserNameExceptMerchantWithPage(@Param("user_name")String user_name,
                                                 @Param("limit") int limit,
                                                 @Param("offset") int offset);
    @Select(SqlUtil.COUNT_SQL + sql_findUsersByUserNameExceptMerchant)
    int getTotal_findUsersByUserNameExceptMerchant(@Param("user_name")String user_name);

    String sql_findUsersByUserNameOnlyMerchant = " from user_t " +
            "where username like concat('%',#{user_name},'%') " +
            "and activation_code<>4 " +
            "and role='MERCHANT' ";
    //通过user_name查找用户（商户）
    @Select("select * " + sql_findUsersByUserNameOnlyMerchant)
    List<User> findUsersByUserNameOnlyMerchant(@Param("user_name")String user_name);

    @Select("select * " + sql_findUsersByUserNameOnlyMerchant + SqlUtil.PAGE_LIMIT_SQL)
    List<User> findUsersByUserNameOnlyMerchantWithPage(@Param("user_name")String user_name,
                                                         @Param("limit") int limit,
                                                         @Param("offset") int offset);
    @Select(SqlUtil.COUNT_SQL + sql_findUsersByUserNameOnlyMerchant)
    int getTotal_findUsersByUserNameOnlyMerchant(@Param("user_name")String user_name);

    String sql_findAllUser = " from user_t where activation_code<>4 ";
    //查找所有用户
    @Select("select * " + sql_findAllUser)
    List<User> findAllUser();

    @Select("select * " + sql_findAllUser + SqlUtil.PAGE_LIMIT_SQL)
    List<User> findAllUserWithPage(
            @Param("limit") int limit,
            @Param("offset") int offset);

    @Select(SqlUtil.COUNT_SQL + sql_findAllUser)
    int getTotal_findAllUser();

    String sql_findAllUserExceptMerchant =  " from user_t " +
            "where activation_code<>4 " +
            "and role<>'MERCHANT' ";
    //查看所有用户（排除商户）
    @Select("select * " + sql_findAllUserExceptMerchant)
    List<User> findAllUserExceptMerchant();

    @Select("select * "+ sql_findAllUserExceptMerchant + SqlUtil.PAGE_LIMIT_SQL)
    List<User> findAllUserExceptMerchantWithPage(
            @Param("limit") int limit,
            @Param("offset") int offset);

    @Select(SqlUtil.COUNT_SQL + sql_findAllUserExceptMerchant)
    int getTotal_findAllUserExceptMerchant();

    //激活user
    @Update("update user_t " +
            "set activation_code = #{code},check_state=1 " +
            "where id = #{id}")
    int setActivationCode(@Param("code") int code,
                          @Param("id") int id);

    //修改密码
    @Update("update user_t " +
            "set password =#{password} " +
            "where id = #{id}")
    int modifyPassword(@Param("password") String password,
                       @Param("id") int id);

    //修改邮箱
    @Update("update user_t " +
            "set email =#{email} " +
            "where id = #{id}")
    int modifyEmail(@Param("email") String email,
                       @Param("id") int id);

    //修改用户信息
    @Update("update user_t " +
            "set " +
            "contact_name=#{contact_name}," +
            "phone=#{phone}," +
            "email=#{email}," +
            "area_code=#{area_code} "+
            "where id=#{id}")
    int updateUser(User user);

    @Update("update user_t " +
            "set " +
            "role=#{role}," +
            "contact_name=#{contact_name}," +
            "phone=#{phone}," +
            "email=#{email}," +
            "area_code=#{area_code} " +
            "where id=#{id}")
    int updateUser2(User user);

    //修改密码
    @Update("update user_t " +
            "set " +
            "password=#{password} " +
            "where id=#{id}")
    int updatePassword(@Param("password")String password,@Param("id")int id);

    //注册user
    @Insert("insert into user_t(username,password,role,contact_name,phone,email,create_date,area_code,activation_code,check_state) " +
            "values(#{username},#{password},#{role},#{contact_name},#{phone},#{email},#{create_date},#{area_code},#{activation_code},#{check_state})")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn = "id")
    int insertUser(User user);

    //删除用户
    @Delete("delete from user_t where id = #{id}")
    int deleteUser(@Param("id") int id);

    String sql_findMerchantUser = " from user_t " +
            "where role = 'MERCHANT' " +
            "and activation_code<>4 ";
    //查看商户的用户信息
    @Select("select * "+sql_findMerchantUser )
    List<User> findMerchantUser();

    @Select("select * " + sql_findMerchantUser + SqlUtil.PAGE_LIMIT_SQL)
    List<User> findMerchantUserWithPage(
            @Param("limit") int limit,
            @Param("offset") int offset);

    @Select(SqlUtil.COUNT_SQL + sql_findMerchantUser)
    int getTotal_findMerchantUser();

    @Select("select * from user_t as u join merchant_t as m " +
            "on u.id=m.user_id " +
            "where m.id=#{merchant_id} ")
    User findUserByMerchant(@Param("merchant_id")int merchant_id);

    String sql_select_field = " u.id,u.username,u.role,u.activation_code," +
            "u.contact_name,u.phone,u.email,u.create_date ";
    String sql_user_join_merchant_join_company = " user_t as u join merchant_t as m on u.id=m.user_id " +
            "join company_t as c on m.id=c.merchant_id ";
    String sql_findMerchantUserByAreaCode = " from " + sql_user_join_merchant_join_company +
            "where c.area_code like concat(#{area_code},'%') " +
            "and u.activation_code<>4 ";
    @Select("select " + sql_select_field + sql_findMerchantUserByAreaCode)
    List<User> findMerchantUserByAreaCode(@Param("area_code") String area_code);
    @Select("select " + sql_select_field + sql_findMerchantUserByAreaCode + SqlUtil.PAGE_LIMIT_SQL)
    List<User> findMerchantUserByAreaCodeWithPage(@Param("area_code") String area_code,
                                                  @Param("limit") int limit,
                                                  @Param("offset") int offset);
    @Select(SqlUtil.COUNT_SQL + sql_findMerchantUserByAreaCode)
    int getTotal_findMerchantUserByAreaCode(@Param("area_code") String area_code);

    @SelectProvider(type=UserMapperProvider.class,method = "findMerchantUsersByConditions")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(property = "qrcode_total", column = "id", one =
            @One(select = "com.scut.originsystem.mapper.MerchantMapper.getMerchantQRcodeTotal")),
            @Result(property = "qrcode_left", column = "id", one =
            @One(select = "com.scut.originsystem.mapper.MerchantMapper.getMerchantQRcodeLeft"))
    })
    List<MerchantUser> findMerchantUsersByConditions(@Param("user_area_code")String user_area_code,
                                                     @Param("area_code")String area_code,
                                                     @Param("create_date_start")String create_date_start,
                                                     @Param("create_date_end")String create_date_end,
                                                     @Param("activation_code")int activation_code);
    @SelectProvider(type=UserMapperProvider.class,method = "findMerchantUsersByConditionsWithPage")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(property = "qrcode_total", column = "id", one =
            @One(select = "com.scut.originsystem.mapper.MerchantMapper.getMerchantQRcodeTotal")),
            @Result(property = "qrcode_left", column = "id", one =
            @One(select = "com.scut.originsystem.mapper.MerchantMapper.getMerchantQRcodeLeft"))
    })
    List<MerchantUser> findMerchantUsersByConditionsWithPage(@Param("user_area_code")String user_area_code,
                                                             @Param("area_code")String area_code,
                                                             @Param("create_date_start")String create_date_start,
                                                             @Param("create_date_end")String create_date_end,
                                                             @Param("activation_code")int activation_code,
                                                             @Param("limit")int limit,
                                                             @Param("offset")int offset);

    @SelectProvider(type = UserMapperProvider.class,method = "getAreaCodeNum_findMerchantUsersByConditions")
    List<AreaPCD> getAreaCodeNum_findMerchantUsersByConditions(@Param("user_area_code")String user_area_code,
                                                               @Param("area_code")String area_code,
                                                               @Param("create_date_start")String create_date_start,
                                                               @Param("create_date_end")String create_date_end,
                                                               @Param("activation_code")int activation_code);

    @SelectProvider(type=UserMapperProvider.class,method = "getTotal_findMerchantUsersByConditions")
    int getTotal_findMerchantUsersByConditions(@Param("user_area_code")String user_area_code,
                                                                     @Param("area_code")String area_code,
                                                                     @Param("create_date_start")String create_date_start,
                                                                     @Param("create_date_end")String create_date_end,
                                                                     @Param("activation_code")int activation_code);
}
