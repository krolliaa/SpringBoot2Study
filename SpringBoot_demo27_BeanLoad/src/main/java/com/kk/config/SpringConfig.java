package com.kk.config;

import com.kk.util.MyImportSelector;
import org.springframework.context.annotation.Import;

@Import(value = {MyImportSelector.class})
public class SpringConfig {
}
