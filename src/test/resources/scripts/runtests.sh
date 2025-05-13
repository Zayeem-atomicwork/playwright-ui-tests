 -st#!/bin/bash

: '
Setting -e to immediately exit if any command has a non-zero exit code.
Setting -u to immediately exit if any variable being used is not defined.
Setting -x to print commands and their arguments as they are executed.
'
set -eux

# Changing the current working directory to the ui-tests project.
cd ${WORKSPACE}/zayeem/ui-tests

mvn_cmd="mvn clean test -DdashboardType=${DASHBOARD} -DenvType=${ENVIRONMENT} -Dheadless=true"

# If BROWSER variable is non-empty, add it to the maven command.
if [[ -n "${BROWSER}" ]]; then
	mvn_cmd="${mvn_cmd} -Dbrowser=${BROWSER}"
fi

# If USER_EMAIL variable is non-empty, add it to the maven command.
if [[ -n "${USER_EMAIL:-}" ]]; then
	mvn_cmd="${mvn_cmd} -Demail=${USER_EMAIL}"
fi

# If PASSWORD variable is non-empty, add it to the maven command.
if [[ -n "${PASSWORD:-}" ]]; then
	mvn_cmd="${mvn_cmd} -Dpassword=${PASSWORD}"
fi

# If ANALYTICS_API_KEY variable is non-empty, add it to the maven command.
if [[ -n "${ANALYTICS_API_KEY:-}" ]]; then
	mvn_cmd="${mvn_cmd} -DapiKey=${ANALYTICS_API_KEY}"
fi

: '
If TAGS variable is non-empty, tests with these tags will run, else, check 
for TEST_RUNNERS variable, if non-empty, all test runners will be triggered
as defined in the testng.xml file.
' 
if [[ -n "${TAGS:-}" ]]; then
	mvn_cmd="${mvn_cmd} -Dcucumber.filter.tags=\"${TAGS}\""
elif [[ -n "${TEST_RUNNERS:-}" ]]; then
	mvn_cmd="${mvn_cmd} -Dtest=${TEST_RUNNERS}"
fi

# If SCENARIO_THREAD_COUNT variable is non-empty, add it to the maven command.
if [[ -n "${SCENARIO_THREAD_COUNT:-}" ]]; then
	mvn_cmd="${mvn_cmd} -DscenarioThreadCount=${SCENARIO_THREAD_COUNT}"
fi

# If FEATURE_THREAD_COUNT variable is non-empty, add it to the maven command.
if [[ -n "${FEATURE_THREAD_COUNT:-}" ]]; then
	mvn_cmd="${mvn_cmd} -DscenarioThreadCount=${FEATURE_THREAD_COUNT}"
fi

# Run the maven command.
${mvn_cmd}

set +eux