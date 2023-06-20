import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.PullRequests
import jetbrains.buildServer.configs.kotlin.buildFeatures.commitStatusPublisher
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildFeatures.pullRequests
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2023.05"

project {

    vcsRoot(HttpsGithubComOstnasAnyRefsHeadsMain)

    buildType(Build)

    template(HttpsGithubComOstnasHelper)
}

object Build : BuildType({
    templates(HttpsGithubComOstnasHelper)
    name = "Build"

    vcs {
        root(HttpsGithubComOstnasAnyRefsHeadsMain)
    }

    steps {
        script {
            id = "RUNNER_3"
            scriptContent = "pwd"
        }
    }

    triggers {
        vcs {
            id = "TRIGGER_2"
        }
    }

    features {
        perfmon {
            id = "perfmon"
        }
        commitStatusPublisher {
            id = "BUILD_EXT_1"
            vcsRootExtId = "${HttpsGithubComOstnasAnyRefsHeadsMain.id}"
            publisher = github {
                githubUrl = "https://api.github.com"
                authType = personalToken {
                    token = "zxx82f183cb4e85be32b8a0770f1e5c16b0dd4121da3bbf4f2117c17b64bfb9123ab424218cbd29df73775d03cbe80d301b"
                }
            }
        }
        pullRequests {
            id = "BUILD_EXT_2"
            vcsRootExtId = "${HttpsGithubComOstnasAnyRefsHeadsMain.id}"
            provider = github {
                authType = token {
                    token = "zxx82f183cb4e85be32b8a0770f1e5c16b0dd4121da3bbf4f2117c17b64bfb9123ab424218cbd29df73775d03cbe80d301b"
                }
                filterAuthorRole = PullRequests.GitHubRoleFilter.MEMBER
            }
        }
    }
})

object HttpsGithubComOstnasHelper : Template({
    name = "Def_pr"

    steps {
        script {
            id = "RUNNER_2"
            scriptContent = "ls"
        }
    }
})

object HttpsGithubComOstnasAnyRefsHeadsMain : GitVcsRoot({
    name = "https://github.com/ostnas/any#refs/heads/main"
    url = "https://github.com/ostnas/any"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = password {
        userName = "ostnas95@gmail.com"
        password = "zxxac0b501ef9a679f40e77b73e4a285b9ffc63c54b1e1ae8fbc7b8c640583c5428de8bd4d87b829a8572bb632d6fd052dc6566d6ad6d3e90e8acdd1807836690909ed97b3c5c976e9243c5247021649737ec0149017f52b956afff02a943c310a6"
    }
})
