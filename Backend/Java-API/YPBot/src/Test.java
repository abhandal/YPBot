



public class Test {

	public static void main(String[] args) {

		String json1 = "{keyPhrases=[hamburger, fries, coke], id=80a9223a-fe33-4bda-a85e-5fe99c2e1aee}";
		String json2 = "{keyPhrases=[hamburger], id=2bfc2ba2-2fa1-4692-8b77-dd70d1b85366}";
		
		String extract = json1.substring(13, json1.indexOf("], "));
		String convertPlus = extract.replaceAll(", ", "+");
		System.out.println(convertPlus);

		

	}

}
