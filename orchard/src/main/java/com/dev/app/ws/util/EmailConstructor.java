package com.dev.app.ws.util;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.dev.app.ws.entities.AppUser;

public class EmailConstructor {

	@Autowired
	private Environment env;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	public MimeMessagePreparator constructNewUserEmail(AppUser appUser, String password) {
		
		Context context = new Context();
		context.setVariable("user", appUser);
		context.setVariable("password", password);
		
		String text = templateEngine.process("newUserEmailTemplate", context);
		
		MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				
				MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
				messageHelper.setPriority(1);
				messageHelper.setTo(appUser.getEmail());
				messageHelper.setSubject("Welcome To Orchard");
				messageHelper.setText(text, true);
				messageHelper.setFrom(new InternetAddress(env.getProperty("support.email")));
			}
		};
		
		return messagePreparator;
	}
	
	public MimeMessagePreparator constructResetPasswordEmail(AppUser appUser, String password) {
		
		Context context = new Context();
		context.setVariable("user", appUser);
		context.setVariable("password", password);
		
		String text = templateEngine.process("resetPasswordEmailTemplate", context);
		
		MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				
				MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
				messageHelper.setPriority(1);
				messageHelper.setTo(appUser.getPassword());
				messageHelper.setSubject("New Password - Orchard");
				messageHelper.setText(text, true);
				messageHelper.setFrom(new InternetAddress(env.getProperty("support.email")));
			}
		};
		
		return messagePreparator;
	}
	
	public MimeMessagePreparator constructUpdateUserProfileEmail(AppUser appUser) {
		
		Context context = new Context();
		context.setVariable("user", appUser);
		
		String text = templateEngine.process("updateUserProfileEmailTemplate", context);
		
		MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				
				MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
				messageHelper.setPriority(1);
				messageHelper.setTo(appUser.getEmail());
				messageHelper.setSubject("Profile Update - Orchard");
				messageHelper.setText(text, true);
				messageHelper.setFrom(new InternetAddress(env.getProperty("support.email")));	
			}
		};
		
		return messagePreparator;
	}
}
