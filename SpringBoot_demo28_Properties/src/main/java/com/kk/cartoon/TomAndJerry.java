package com.kk.cartoon;

import com.kk.bean.Cat;
import com.kk.bean.Mouse;
import com.kk.util.MyConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@EnableConfigurationProperties(value = {MyConfigurationProperties.class})
//@Component(value = "tomAndJerry")
public class TomAndJerry {
    private Cat cat;
    private Mouse mouse;

    private MyConfigurationProperties myConfigurationProperties;

    public TomAndJerry(MyConfigurationProperties myConfigurationProperties) {
        this.myConfigurationProperties = myConfigurationProperties;
        cat = new Cat();
        mouse = new Mouse();
        this.cat.setName((myConfigurationProperties.getCat() != null && myConfigurationProperties.getCat().getName() != "") ? myConfigurationProperties.getCat().getName() : "出错啦");
        this.cat.setAge((myConfigurationProperties.getCat() != null && myConfigurationProperties.getCat().getAge() != "") ? myConfigurationProperties.getCat().getAge() : "出错啦");
        this.mouse.setName((myConfigurationProperties.getMouse() != null && myConfigurationProperties.getMouse().getName() != "") ? myConfigurationProperties.getMouse().getName() : "出错啦");
        this.mouse.setAge((myConfigurationProperties.getMouse() != null && myConfigurationProperties.getMouse().getAge() != "") ? myConfigurationProperties.getMouse().getAge() : "出错啦");
    }

    public void playCartoon() {
        System.out.println(cat.getAge() + "岁的" + cat.getName() + "跟" + mouse.getAge() + "岁的" + mouse.getName() + "在干架！");
    }
}
