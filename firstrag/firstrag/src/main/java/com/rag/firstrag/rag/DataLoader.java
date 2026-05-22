package com.rag.firstrag.rag;

import jakarta.annotation.PostConstruct;
//import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public class DataLoader {

    private final VectorStore vectorStore;

    public DataLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadSampleDocuments() {
        List<Document> documentList = List.of(

                // ─── PRODUCT CATALOG ───────────────────────────────────────────

                new Document(
                        "Product: Apple iPhone 15 Pro. Category: Smartphone. Price: $999. " +
                                "Features: A17 Pro chip, 48MP triple camera, titanium frame, USB-C, Action Button, " +
                                "6.1-inch Super Retina XDR display. Colors: Natural Titanium, Blue Titanium, Black Titanium. " +
                                "Battery: Up to 23 hours. Storage: 128GB, 256GB, 512GB, 1TB.",
                        Map.of("category", "product", "type", "smartphone", "brand", "Apple", "price", "999")
                ),

                new Document(
                        "Product: Sony WH-1000XM5 Headphones. Category: Audio. Price: $349. " +
                                "Features: Industry-leading noise cancellation, 30-hour battery, multipoint Bluetooth 5.2, " +
                                "quick charge (3 min = 3 hrs playback), foldable design. Weight: 250g.",
                        Map.of("category", "product", "type", "audio", "brand", "Sony", "price", "349")
                ),

                new Document(
                        "Product: Samsung 65-inch QLED 4K Smart TV QN65Q80C. Price: $1,299. " +
                                "Features: Quantum HDR+, 120Hz refresh rate, 4 HDMI ports, Gaming Hub, " +
                                "built-in Alexa and Google Assistant, SmartThings compatible.",
                        Map.of("category", "product", "type", "television", "brand", "Samsung", "price", "1299")
                ),

                new Document(
                        "Product: Nike Air Max 270. Category: Footwear. Price: $150. " +
                                "Features: Largest Air heel unit for max cushioning, mesh upper, rubber outsole. " +
                                "Sizes: US 6–15. Best for: casual wear, light running, all-day comfort.",
                        Map.of("category", "product", "type", "footwear", "brand", "Nike", "price", "150")
                ),

                new Document(
                        "Product: Dyson V15 Detect Vacuum. Category: Home Appliance. Price: $749. " +
                                "Features: Laser dust detection, HEPA filtration, 60-min battery, LCD screen showing " +
                                "real-time particle count, 3 power modes, wall-mounted charging dock included.",
                        Map.of("category", "product", "type", "appliance", "brand", "Dyson", "price", "749")
                ),

                // ─── LIFESTYLE & WELLNESS ──────────────────────────────────────

                new Document(
                        "Lifestyle: Morning Routine for Productivity. " +
                                "Start with 10 minutes of meditation to reduce cortisol. Follow with 20 minutes of exercise. " +
                                "Eat a high-protein breakfast (eggs, Greek yogurt, oats). Avoid social media for the first hour. " +
                                "Journal 3 gratitude points. Linked to improved focus, lower anxiety, higher output.",
                        Map.of("category", "lifestyle", "type", "routine", "topic", "productivity")
                ),

                new Document(
                        "Wellness: Sleep Optimization Guide. " +
                                "Adults need 7–9 hours per night. Tips: consistent sleep schedule, avoid caffeine after 2 PM, " +
                                "room temperature 65–68°F (18–20°C), blackout curtains, no screens 1 hour before bed — " +
                                "blue light suppresses melatonin. Consider magnesium glycinate supplementation.",
                        Map.of("category", "lifestyle", "type", "wellness", "topic", "sleep")
                ),

                new Document(
                        "Nutrition: Mediterranean Diet Overview. " +
                                "Emphasizes whole grains, legumes, vegetables, fruits, olive oil, and oily fish (salmon, sardines). " +
                                "Limits red meat, processed foods, refined sugars. " +
                                "Benefits: reduced heart disease risk, lower type 2 diabetes risk, better cognitive health. " +
                                "Key foods: hummus, lentils, quinoa, avocado, walnuts.",
                        Map.of("category", "lifestyle", "type", "nutrition", "topic", "diet")
                ),

                new Document(
                        "Fitness: 4-Week Beginner Strength Training Plan. " +
                                "Week 1–2: Full body 3x/week — squats, push-ups, dumbbell rows, plank. " +
                                "Week 3–4: Upper/lower split. Rest 48h between same muscle groups. " +
                                "Progressive overload: increase weight or reps by 5–10% weekly. " +
                                "Warm up 5–10 min, cool down with stretching.",
                        Map.of("category", "lifestyle", "type", "fitness", "topic", "strength_training")
                ),

                new Document(
                        "Lifestyle: Digital Detox Tips. " +
                                "Limit screen time to under 2 hours of recreational use per day. " +
                                "Use app timers for Instagram, YouTube, TikTok. Enable grayscale mode to reduce phone appeal. " +
                                "Replace scrolling with reading, walking, or journaling. " +
                                "No phones at the dining table or in the bedroom after 10 PM.",
                        Map.of("category", "lifestyle", "type", "wellness", "topic", "digital_detox")
                ),

                // ─── HR & COMPANY POLICY ───────────────────────────────────────

                new Document(
                        "HR Policy: Annual Leave Entitlement. " +
                                "Full-time employees receive 24 days paid leave per year. Part-time on pro-rata basis. " +
                                "Requires manager approval 2 weeks in advance. Up to 5 days can be carried over. " +
                                "Minimum 10 consecutive days must be taken annually for employee wellbeing.",
                        Map.of("category", "hr", "type", "policy", "topic", "leave")
                ),

                new Document(
                        "HR Policy: Remote Work Guidelines. " +
                                "Up to 3 remote days per week with manager approval. Core hours: 10 AM–3 PM local time. " +
                                "Minimum internet speed: 25 Mbps. One-time home office stipend: $500. " +
                                "Must be reachable on Slack and email during core hours. Quarterly office attendance required.",
                        Map.of("category", "hr", "type", "policy", "topic", "remote_work")
                ),

                new Document(
                        "HR Policy: Performance Review Process. " +
                                "Reviews held bi-annually in June and December. Self-assessment submitted 2 weeks prior. " +
                                "Ratings: Exceeds Expectations, Meets Expectations, Needs Improvement. " +
                                "Two consecutive 'Exceeds' ratings qualify for promotion consideration. " +
                                "Salary increases are merit-based and tied to department budget.",
                        Map.of("category", "hr", "type", "policy", "topic", "performance")
                ),

                new Document(
                        "HR Policy: Code of Conduct. " +
                                "All employees must maintain professional behaviour in the workplace and online. " +
                                "Zero tolerance for harassment, discrimination, or bullying. " +
                                "Conflicts of interest must be declared to HR in writing. " +
                                "Confidential company data must not be shared externally without written approval. " +
                                "Violations may result in disciplinary action including termination.",
                        Map.of("category", "hr", "type", "policy", "topic", "conduct")
                ),

                // ─── FINANCE & BANKING ─────────────────────────────────────────

                new Document(
                        "Finance: Understanding Compound Interest. " +
                                "Formula: A = P(1 + r/n)^(nt). P = principal, r = annual rate, n = compounding frequency, t = years. " +
                                "Example: $10,000 at 6% compounded monthly for 10 years = $18,193.97. " +
                                "The earlier you invest, the greater the compounding effect. Time in market beats timing the market.",
                        Map.of("category", "finance", "type", "education", "topic", "investing")
                ),

                new Document(
                        "Finance: Building an Emergency Fund. " +
                                "Target: 3–6 months of essential living expenses (rent, utilities, groceries, insurance, transport). " +
                                "Store in a high-yield savings account (HYSA) for easy access and modest growth. " +
                                "Automate fixed transfers on every payday. " +
                                "Never invest emergency funds in stocks due to volatility risk.",
                        Map.of("category", "finance", "type", "education", "topic", "savings")
                ),

                new Document(
                        "Banking FAQ: Disputing a Fraudulent Transaction. " +
                                "Step 1: Call the number on the back of your card immediately. " +
                                "Step 2: Freeze your card via the mobile app. " +
                                "Step 3: Complete the online fraud dispute form. " +
                                "Step 4: Bank investigates within 10 business days. " +
                                "Step 5: Provisional credit may be applied during investigation. Keep all receipts as evidence.",
                        Map.of("category", "finance", "type", "faq", "topic", "fraud")
                ),

                // ─── TECH SUPPORT ──────────────────────────────────────────────

                new Document(
                        "Tech Support: Password Reset Steps. " +
                                "Step 1: Click 'Forgot Password' on the login page. " +
                                "Step 2: Enter your registered email and click 'Send Reset Link'. " +
                                "Step 3: Check inbox and spam folder. Link expires in 15 minutes. " +
                                "Step 4: Set new password — min 8 chars, 1 uppercase, 1 number, 1 symbol. " +
                                "If no email received, contact support@company.com.",
                        Map.of("category", "tech_support", "type", "faq", "topic", "account")
                ),

                new Document(
                        "Tech Support: Troubleshooting Slow Internet. " +
                                "Step 1: Restart router — unplug 30 seconds, replug. " +
                                "Step 2: Run speed test at fast.com. " +
                                "Step 3: Switch from WiFi to Ethernet for stability. " +
                                "Step 4: Reduce simultaneous streaming devices. " +
                                "Step 5: Check ISP outage map. If speeds consistently below plan, contact your ISP.",
                        Map.of("category", "tech_support", "type", "troubleshooting", "topic", "network")
                ),

                new Document(
                        "Tech Support: App Crash on Mobile. " +
                                "Step 1: Force close the app and reopen. " +
                                "Step 2: Clear app cache — Settings > Apps > [App Name] > Clear Cache. " +
                                "Step 3: Update the app from the App Store or Play Store. " +
                                "Step 4: Restart your device. " +
                                "Step 5: Uninstall and reinstall the app. If issue persists, report via in-app feedback.",
                        Map.of("category", "tech_support", "type", "troubleshooting", "topic", "mobile_app")
                )

        );

        vectorStore.add(documentList);
//        log.info("Loaded {} documents into the vector store.", documentList.size());
    }
}