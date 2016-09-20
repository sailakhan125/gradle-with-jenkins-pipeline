#!/usr/bin/env groovy

def gradle(args) {
	def run = isUnix() ? sh : bat

	return run "gradle ${args}";
}

node ("git && gradle && jdk8") {
	stage "clean"
	deleteDir()

	stage "scm"
	checkout scm

	stage "gradle-clean"
	gradle "clean"

	stage "compile"
	gradle "classes"

	stage "compile-tests"
	gradle "testClasses"

	stage "test"
	gradle "test"
	junit "build/**/TEST-*.xml"

	stage "check"
	gradle "check"
}
