package com.example.amanscode.rulesengine.main.example1;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.rules.RuleRuntime;
import javax.rules.RuleServiceProvider;
import javax.rules.RuleServiceProviderManager;
import javax.rules.StatelessRuleSession;
import javax.rules.admin.RuleAdministrator;
import javax.rules.admin.RuleExecutionSet;

public class Example1 {

	public Example1() {

		try {
			// Load the rule service provider of the reference
			// implementation.
			// Loading this class will automatically register this
			// provider with the provider manager.
			Class.forName("org.jruleengine.RuleServiceProviderImpl");

			// Get the rule service provider from the provider manager.
			RuleServiceProvider serviceProvider = RuleServiceProviderManager.getRuleServiceProvider("org.jruleengine");

			// get the RuleAdministrator
			RuleAdministrator ruleAdministrator = serviceProvider.getRuleAdministrator();
			System.out.println("Acquired RuleAdministrator: " + ruleAdministrator);

			// get an input stream to a test XML ruleset
			// This rule execution set is part of the TCK.
			String ruleFileName = "C:\\\\Eclipse Workspaces\\\\Workspace_1\\\\rulesengine\\\\src\\\\main\\\\resources\\\\rules\\\\example1.xml";
			InputStream inStream = new FileInputStream(ruleFileName);
			System.out.println("Using Rules File: " + ruleFileName);

			// parse the ruleset from the XML document
			RuleExecutionSet res1 = ruleAdministrator.getLocalRuleExecutionSetProvider(null)
					.createRuleExecutionSet(inStream, null);
			inStream.close();

			// register the RuleExecutionSet
			String uri = res1.getName();
			ruleAdministrator.registerRuleExecutionSet(uri, res1, null);
			System.out.println("Loaded RuleExecutionSet: " + res1.getName());

			RuleRuntime ruleRuntime = serviceProvider.getRuleRuntime();
			System.out.println("Acquired RuleRuntime: " + ruleRuntime);

			// create a StatelessRuleSession
			StatelessRuleSession statelessRuleSession = (StatelessRuleSession) ruleRuntime.createRuleSession(uri,
					new HashMap(), RuleRuntime.STATELESS_SESSION_TYPE);

			System.out.println("Got Stateless Rule Session: " + statelessRuleSession);

			// call executeRules with some input objects

			// Create a Customer as specified by the TCK documentation.

			Scanner scanner = new Scanner(System.in);
			System.out.println();
			System.out.println("Enter a price to know the discount");
			String scanner_input = scanner.nextLine();
			Price price = new Price();
			price.setActualPrice(Integer.parseInt(scanner_input));

			// Create a input list.
			List input = new ArrayList();
			input.add(price);

			// Print the input.
			System.out.println("Calling rule session with actual price: " + price.getActualPrice());

			// Execute the rules without a filter.
			List results = statelessRuleSession.executeRules(input);

			System.out.println("Called executeRules on Stateless Rule Session: " + statelessRuleSession);

			System.out.println("Result of calling executeRules: " + results.size() + " results.");
			System.out.println();

			// Loop over the results.
			Iterator itr = results.iterator();
			while (itr.hasNext()) {
				Object obj = itr.next();
				System.out.println();
				System.out.println("Actual Price: " + ((Price) obj).getActualPrice());
				System.out.println("Discount: " + ((Price) obj).getDiscount());
				System.out.println("Discounted Price: " + ((Price) obj).getDiscountedPrice());
				System.out.println();
			}

			// Release the session.
			statelessRuleSession.release();
			System.out.println("Released Stateless Rule Session.");
			System.out.println();

		} catch (NoClassDefFoundError e) {
			if (e.getMessage().indexOf("Exception") != -1) {
				System.err.println("Error: The Rule Engine Implementation could not be found.");
			} else {
				System.err.println("Error: " + e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		Example1 example = new Example1();
	}

}