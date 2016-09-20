#!/usr/bin/env groovy

def gradle(args) {
	def command = "gradle ${args}";
	return isUnix()
		? sh(command)
		: bat(command)
}

node ("git && gradle && jdk8") {
	stage("clean") {
		deleteDir()
	}

	stage("scm") {
		checkout scm
	}

	stage("gradle-clean") {
		gradle "clean"
	}

	stage("compile") {
		gradle "classes"
	}

	stage("compile-tests") {
		gradle "testClasses"
	}

	stage("test") {
		try {
			gradle "test"
		} catch (e) {
			return
		} finally {
			junit "build/**/TEST-*.xml"
		}
	}

	stage("check") {
		gradle "check"
	}
}
