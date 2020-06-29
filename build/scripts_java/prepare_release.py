import os
from typing import Callable

from scripts.utilities import run, check_step, assert_step
from scripts.version import Version
from scripts_java.documentation_release import PrepareDocumentationRelease
from scripts.git_utilities import GitUtilities
from scripts_java.release_constants import release_constants
from scripts_java.release_details import ReleaseDetails
from scripts_java.starter_project_release import PrepareStarterProjectRelease
from git import Repo

class PrepareRelease:
    def __init__(self, details: ReleaseDetails) -> None:
        self.details = details


def publish_to_maven(details: ReleaseDetails) -> None:
    new = details.new_version.get_version_text_without_v()
    run(["mvn", "versions:set", f"-DnewVersion={new}"])
    run(["./publish_maven.sh"])


def set_snapshot(details: ReleaseDetails) -> None:
    next = details.new_version.update_patch().get_version_text_without_v()
    run(["mvn", "versions:set", f"-DnewVersion={next}-SNAPSHOT"])


def check_repo(release_details: ReleaseDetails) -> None:
    # check we are on a master branch that is in sync
    repo = Repo(".")
    assert_step((repo.active_branch.name == 'master'))
    GitUtilities.check_no_uncommitted_changes(repo);
    assert_step(len(
        list(repo.iter_commits('master@{u}..master'))) == 0,
                f"there are un-pushed changes in approvaltests")
    GitUtilities.pull_active_branch_origin()


def build(update_version: Callable[[Version], Version]) -> None:
    old_version = load_current_version()
    new_version = update_version(old_version)
    release_details = ReleaseDetails(old_version, new_version)

    check_repo(release_details)
    # publish_to_maven(release_details)
    # PrepareDocumentationRelease.prepare_documentation(release_details)
    # GitUtilities.add_and_commit_everything(".", new_version.get_version_text());
    # set_snapshot(release_details)
    # GitUtilities.add_and_commit_everything(".", "set Snapshot");
    # PrepareStarterProjectRelease.prepare_starter_project(release_details)
    # GitUtilities.push_active_branch_origin();
    # print("Done")


def load_current_version() -> Version:
    os.chdir("../../ApprovalTests.java")
    return Version.read(release_constants.build_dir)
