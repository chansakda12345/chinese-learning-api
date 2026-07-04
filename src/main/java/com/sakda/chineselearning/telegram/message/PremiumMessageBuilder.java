package com.sakda.chineselearning.telegram.message;

import org.springframework.stereotype.Component;

@Component
public class PremiumMessageBuilder {

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

}
