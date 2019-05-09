package com.demo.service;

public interface EmailService {
	
	/**
	 * 发送文本邮件
	 */
	public void sendSimpleMail(String to, String subject, String content);
	
	/**
	 * 发送html邮件
	 */
    public void sendHtmlMail(String to, String subject, String content);

    /**
	 * 发送带附件的邮件
	 */
    public void sendAttachmentsMail(String to, String subject, String content, String filePath);

    /**
     * 发送正文中有静态资源（图片）的邮件
     */
    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId);

}
