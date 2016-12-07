#!/usr/bin/env groovy

def jenkinsfile
fileLoader.withGit('https://git.sits.no/git/scm/ao/aurora-pipeline-scripts.git', 'v1.0.0') {
   jenkinsfile = fileLoader.load('templates/bibliotek')
}

jenkinsfile.run('06e1940293ab978f7444e4815083f1a4fb576026', 'Maven 3', 'ci_aos', 'ci_aos')