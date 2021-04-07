package ru.croc.services.dataservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
public class DateTest {
    @Test
    public void stringToData() {
        Assertions.assertEquals("от двадцать девятого ноября две тысячи первого года",
                StringToData.isPatterned("от 29.11.2001") );

    }
}
