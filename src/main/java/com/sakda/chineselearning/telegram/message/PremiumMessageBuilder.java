package com.sakda.chineselearning.telegram.message;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PremiumMessageBuilder {
	
	@Value("${telegram.admin-contact}")
	private String adminContact;
	
	@Value("${telegram.public-channel-link}")
	private String publicChannelLink;
	
	@Value("${telegram.premium-channel-link}")
	private String premiumChannelLink;

	public String buildPremiumWelcomeMessage(String studentName, int daysAdded) {
		return """
				🎉 សូមអបអរសាទរ %s! (Congratulations!)
				
				ការបង់ប្រាក់របស់អ្នកទទួលបានជោគជ័យ! ឥឡូវនេះអ្នកគឺជាសិស្សប្រីមីញ៉ូមរយៈពេល %d ថ្ងៃ។
				
				ប្រសិនបើអ្នកត្រូវការជំនួយ សូមទាក់ទងមកកាន់ Admin ផ្ទាល់តាមរយៈ៖ %s
				
				👉 ចុចទីនេះដើម្បីចូលរួមក្រុមសិក្សា៖
				1. ប៉ុស្តិ៍សាធារណៈ (Public Channel): %s
				2. ប៉ុស្តិ៍ប្រីមីញ៉ូម (Premium Channel): %s
				""".formatted(studentName, daysAdded, adminContact, publicChannelLink, premiumChannelLink);
	}
	
	public String buildAccountNotLinkedMessage() {
		
		return """
                ⚠️ គណនីមិនទាន់ភ្ជាប់ (Account Not Linked)
                សូមចូលទៅកាន់គេហទំព័ររបស់យើង រួចចម្លងកូដភ្ជាប់គណនី រួចផ្ញើមកទីនេះជាមួយបញ្ជា៖
                /link <កូដរបស់អ្នក> ដើម្បីចាប់ផ្តើមរៀន។	
                """;
	}
	
	public String buildPremiumOnlyMessage() {
        return """
                🔒 មុខងារនេះ ប្រើបានសម្រេាប់តែសិស្សជាសមាជិកតែប៉ុណ្ណោះ (Premium Feature Only)
                សុំទោស! មុខងារនេះ ប្រើបានសម្រេាប់តែសិស្សជាសមាជិកតែប៉ុណ្ណោះ៕
                សូមចុះឈ្មោះជាសមាជិកប្រចាំខែនៅលើគេហទំព័ររបស់យើង ដើម្បីទទួលបានការបង្រៀនផ្ទាល់ខ្លួន ការធ្វើតេស្ត និងការរំលឹកឡើងវិញ!
                """;
    }
	
	public String buildPremiumExpiringSoonMessage(String studentName) {
		return """
				⚠️ សេចក្តីជូនដំណឹង! (Reminder!)
				
				សួស្តី %s, គណនីប្រីមីញ៉ូមរបស់អ្នកនឹងផុតកំណត់ក្នុងរយៈពេល ៣ ថ្ងៃទៀត!
				(Hello %s, your Premium Subscription will expire in 3 days!)
				
				ដើម្បីបន្តការសិក្សាដោយមិនមានការរំខាន សូមទាក់ទងមកកាន់ Admin របស់យើង៖ %s
				(To continue learning without interruption, please contact our Admin to renew: %s)
				""".formatted(studentName, studentName, adminContact, adminContact);
	}

}
