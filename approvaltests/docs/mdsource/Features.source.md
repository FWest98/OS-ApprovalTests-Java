<a id="top"></a>

# Features



<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Contents**

- [Using Alternative Base Directory for Approval Output Files](#using-alternative-base-directory-for-approval-output-files)
- [Using Subdirectories for Approval Output Files](#using-subdirectories-for-approval-output-files)
- [PackageLevelSettings](#packagelevelsettings)
- [Arlos Git Notation Prompt](#arlos-git-notation-prompt)
- [Faster Test Commit Revert (TCR)](#faster-test-commit-revert-tcr)
- [Test Commit Revert (TCR)](#test-commit-revert-tcr)
  - [Method 1 - extend class](#method-1---extend-class)
  - [Method 2 - use Runner](#method-2---use-runner)
  - [Results](#results)
  - [Usage](#usage)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Using Alternative Base Directory for Approval Output Files
Q: what if I want my output files to show up under the `/resources/` folder?
A: you can do that, [here's how](Configuration.md#using-alternative-base-directory-for-approval-output-file)


## Using Subdirectories for Approval Output Files
Approved and received files can be stored in a preferred location. 
[More here](Configuration.md#using-subdirectories-for-approval-output-files)

## PackageLevelSettings

Package Level Settings allows for programmatic setting of configuration at the package level.
[More Here](Configuration.md#packagelevelsetting)

## Arlos Git Notation Prompt

This is a prompt that includes helper text and quick actions for use with [Arlo's Commit Notation](https://github.com/RefactoringCombos/ArlosCommitNotation).
It is currently the default option for TCR and will produce a window that looks like:

![prompt](/approvaltests/src/test/java/machine_specific_tests/approvaltests/testcommitrevert/ArlosGitNotationPromptTest.test.Mac_OS_X.approved.png)



## Faster Test Commit Revert (TCR)

The startup time for swing is around 3 seconds (on my mac). This is painfully slow in some situations. 
FasterTestCommitRevertRunner & FasterTestCommitRevertTest Solve this by shelling out to applescript, which 
brings the time down to a few milliseconds. 

Note: this currently only works on mac and will default to the normal swing otherwise. 

## Test Commit Revert (TCR)

This is a convenience class to follow the practice 'Test Commit/Revert' from Kent Beck. Simply adding
### Method 1 - extend class
snippet: test_commit_revert
to your test class

### Method 2 - use Runner
snippet: test_commit_revert_runner
to annotate your class

### Results 

Whichever method you use, on test run TRC will invoke 1 of 2 options

*  On success
A dialog will appear asking for a commit message. Once given it will commit all files with that message
![prompt](images/commit_dialog.png)

* On failure
It will revert all changes

Note: this currently only works with git

### Usage

Use this with caution. I find Test Commit/Revert helpful when doing a strict refactoring. It is unusable when creating approvaltests as the first run always fails and gets reverted.
Often I will have a test extend TCR for short periods then remove it after I'm out of a refactoring mode.

This also works well with [Arlo Belshee's Git Notation](https://github.com/RefactoringCombos/ArlosCommitNotation) 


---

[Back to User Guide](README.md#top)
