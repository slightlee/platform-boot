package com.demain.platform.core.config;

import com.demain.platform.core.handler.ResponseResultInterceptor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 添加自定义拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ResponseResultInterceptor()).addPathPatterns("/**");
    }

    /**
     * 跨域配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                // 允许跨域的域名
                //.allowedOrigins("http://yyy.com", "http://xxx.com")
                // 允许所有域
                .allowedOriginPatterns("*")
                // 允许任何方法（post、get等）
                .allowedMethods("*")
                // 允许任何请求头
                .allowedHeaders("*")
                // 允许证书、cookie
                .allowCredentials(true)
                .exposedHeaders(HttpHeaders.SET_COOKIE)
                // maxAge(3600)表明在3600秒内，不需要再发送预检验请求，可以缓存该结果
                .maxAge(3600L);
    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        //清理掉列表中的所有http message converter
        converters.clear();
        //只设置一个json的converter
        converters.add(new MappingJackson2HttpMessageConverter());

        converters.removeIf(converter -> converter instanceof MappingJackson2HttpMessageConverter);
        converters.add(jackson2HttpMessageConverter());

    }


    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");


    /**
     * 时间格式转换器,将Date类型统一转换为yyyy-MM-dd HH:mm:ss格式的字符串
     * 长整型转换成String
     * 忽略value为null时key的输出
     */
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = serializingObjectMapper();
        // 序列化枚举值
        objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        //忽略value为null时key的输出
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //序列化成json时，将所有的Long变成string
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    /**
     * jackson2 json序列化 null字段输出为空串
     */
    private ObjectMapper serializingObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //序列化日期格式
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer());
        javaTimeModule.addSerializer(Date.class, new DateSerializer(false, simpleDateFormat));
        //反序列化日期格式
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer());
        javaTimeModule.addDeserializer(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                String date = jsonParser.getText();
                try {
                    return simpleDateFormat.parse(date);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        objectMapper.registerModule(javaTimeModule);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

        return objectMapper;
    }


    /**
     * LocalDateTime序列化
     */
    private class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.format(DATETIME_FORMATTER));
        }
    }

    /**
     * LocalDateTime反序列化
     */
    private class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return LocalDateTime.parse(p.getValueAsString(), DATETIME_FORMATTER);
        }
    }

    /**
     * LocalDate序列化
     */
    private class LocalDateSerializer extends JsonSerializer<LocalDate> {

        @Override
        public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.format(DATE_FORMATTER));
        }
    }

    /**
     * LocalDate反序列化
     */
    private class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

        @Override
        public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return LocalDate.parse(p.getValueAsString(), DATE_FORMATTER);
        }
    }

    /**
     * LocalTime序列化
     */
    private class LocalTimeSerializer extends JsonSerializer<LocalTime> {

        @Override
        public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.format(TIME_FORMATTER));
        }
    }

    /**
     * LocalTime反序列化
     */
    private class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {

        @Override
        public LocalTime deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
            return LocalTime.parse(p.getValueAsString(), TIME_FORMATTER);
        }
    }




    /**
     * FastJson 转换器
     */
//    @Bean
//    FastJsonHttpMessageConverter fastJsonHttpMessageConverter () {
//
//        // 定义 converter 转换消息对象
//        FastJsonHttpMessageConverter converter= new FastJsonHttpMessageConverter();
//        // 创建FastJson转换器的配置
//        FastJsonConfig config = new FastJsonConfig();
//        // 处理日期
//        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
//        config.setCharset(Charset.forName("UTF-8"));
//        //处理中文乱码
//        List<MediaType> supportMediaType=new ArrayList<MediaType>();
//        supportMediaType.add(MediaType.APPLICATION_JSON_UTF8);
//        converter.setSupportedMediaTypes(supportMediaType);
//
//        converter.setFastJsonConfig(config);
//        return converter;
//    }


    /**
     * 处理 自定义 异常
     * {
     *   "code": 4004,
     *   "msg": "问卷标识不能为空;问卷ID不能为空",
     *   "data": null
     * }
     * 返回多个信息问题
     *
     * validate参数校验默认的是一个参数校验失败后，还会继续校验后面的参数
     * 通过这个配置改成：校验参数时只要出现校验失败的情况，就立即抛出对应的异常，结束校验，不再进行后续的校验
     */
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                //failFast的意思只要出现校验失败的情况，就立即结束校验，不再进行后续的校验。
                .failFast(true)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
        methodValidationPostProcessor.setValidator(validator());
        return methodValidationPostProcessor;
    }

}
