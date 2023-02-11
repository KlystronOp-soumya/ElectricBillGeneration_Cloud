package com.demo.taskdemo.repo.intf;

import java.util.Date;

public interface DateValidator {

	boolean validate(Date insertionDate, Date billProcessDate);
}
