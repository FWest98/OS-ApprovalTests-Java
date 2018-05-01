ApprovalTests.Java
==================
Capturing Human Intelligence - ApprovalTests is an open source assertion/verification library to aid unit testing.

It is compatible with JUnit 3 & 4 and TestNG

What can it be used for?
---

Approval Tests can be used for verifying objects that require more than a simple assert. They also come prepackaged with utilities for some common .Net scenarios including


- HashMaps & Collections
- Long Strings
- Log Files
- JPanels
- Xml
- Html
- Json

How to get it
---
Unfortunately, I haven't added this to maven repo yet. Simply download the zip from the releases tab and add the jar to your project.


[Video Tutorials](http://www.youtube.com/playlist?list=PLFBA98F47156EFAA9&feature=view_all)
---

You can watch a bunch of short videos on getting started and [using ApprovalTests in Java](http://www.youtube.com/playlist?list=PLFBA98F47156EFAA9&feature=view_all) at youtube

Podcasts
---
If you prefer auditory learning, you might enjoy the following podcast (Note: Some of these talk about the .net side)

- [Cucumber Podcast](https://cucumber.io/blog/2017/01/26/approval-testing)
- [Hanselminutes](http://www.hanselminutes.com/360/approval-tests-with-llewellyn-falco)
- [Herding Code](http://www.developerfusion.com/media/122649/herding-code-117-llewellyn-falcon-on-approval-tests/)
- [The Watir Podcast](http://watirpodcast.com/podcast-53/)


Download
---
You can find the latest ApprovalTests release under https://github.com/approvals/ApprovalTests.Java/releases.


Examples
---
ApprovalTests eats it own dogfood, so the best examples are in the source code itself.

None the less,  Here's a quick look at some
[Sample Code](https://github.com/approvals/ApprovalTests.Java/blob/master/java/org/approvaltests/tests/demos/SampleArrayTest.java)

	public class SampleArrayTest extends TestCase
	{
		public void testList() throws Exception
		{
			String[] names = {"Llewellyn", "James", "Dan", "Jason", "Katrina"};
			Arrays.sort(names);
			Approvals.verifyAll("", names);
		}
	}

Will Produce a File

    SampleTest.TestList.received.txt
    [0] = Dan
    [1] = James
    [2] = Jason
    [3] = Katrina
    [4] = Llewellyn

Simply rename this to SampleTest.testList.approved.txt and the test will now pass.

Approved File Artifacts
---

The `*.approved.*` files must be checked into source your source control. This can be an issue with git as it will change the line endings.
The suggested fix is to add
`*.approved.* binary` to your `.gitattributes`

More Info
---

- [Website](http://approvaltests.sourceforge.net/)
- [Blog](http://blog.approvaltests.com/)
- [Getting Started Doc](https://github.com/approvals/ApprovalTests.Java/blob/master/build/resources/approval_tests/documentation/ApprovalTests%20-%20GettingStarted.md)


## LICENSE
[Apache 2.0 License](https://github.com/SignalR/SignalR/blob/master/LICENSE.md)


Questions?
---

twitter: [@LlewellynFalco](https://twitter.com/#!/llewellynfalco) or #ApprovalTests

Developer notes
----------------

We are currently migrating the build from Ant to Maven. To build with Maven, first you must download the ApprovalTests.jar package from 
[Releases](https://github.com/approvals/ApprovalTests.Java/releases) and install it in your local maven repository:

	mvn install:install-file -Dfile=ApprovalTests.jar -DgroupId=com.approvaltests -DartifactId=approvals -Dversion=020 -Dpackaging=jar

All the other dependencies should be available on Maven central, so this command should then build the project:

	mvn install

Unfortunately right now we have test failures so this will work to install the jars locally as a temporary fix while we sort that out:

	mvn install -DskipTests

I also had trouble with the "mrunit" package which is listed on Maven central but which wouldn't download for me. To fix that you can always install it locally with this command:

	mvn install:install-file -Dfile=java/jars/hadoop/mrunit-0.9.0-incubating-hadoop1.jar -DgroupId=org.apache.mrunit -DartifactId=mrunit -Dversion=0.9.0-incubating -Dpackaging=jar


If you would like to build this project with Apache ant,
then use these commands:

     ant "Publish    ApprovalTests-Util" -buildfile build/build.xml
     cp approvaltests-util/target/approvaltests-util.jar java/jars/
     ant "Publish    ApprovalTests" -buildfile build/build.xml
     cp approvals/target/ApprovalTests.jar java/jars
     ant "Publish    HtmlLocker" -buildfile build/build.xml
     ant "Publish    CounterDisplay" -buildfile build/build.xml
     ant "Publish    approvaltests-hadoop" -buildfile build/build.xml
     ant "Publish    approvaltests-testng" -buildfile build/build.xml

This will build jar files under the target folder for each respective project. At present you have to 
copy the built jar files by hand in between ant steps, since the subprojects depned on one another.
Soon this will be handled by maven instead.
