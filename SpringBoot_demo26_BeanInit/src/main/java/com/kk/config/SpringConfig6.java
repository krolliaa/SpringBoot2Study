package com.kk.config;

import com.kk.util.MyImportSelector;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

//@Configuration
@Import(value = {MyImportSelector.class})
public class SpringConfig6 {
}
