coverage-maven-plugin
=====================

A maven plugin that will blame on pull requests pointing out
chunks of code with low code coverage.

## Usage

You have to setup Cobertura plugin to generate the output as XML:

```xml
...
<build>
  <plugins>
    <plugin>
      <groupId>org.codehaus.mojo</groupId>
      <artifactId>cobertura-maven-plugin</artifactId>
      <configuration>
        <formats>
          <format>html</format>
          <format>xml</format>
        </formats>
      </configuration>
    </plugin>
  </plugins>
</build>
...
```

And run the following command:

```bash
mvn clean install cobertura:cobertura \
  com.contaazul:coverage-maven-plugin:publish \
  -Dgithub.pullRequestId=${ID_OF_THE_PULL_REQUEST} \
  -Dgithub.repositoryOwner=${REPOSITORY_OWNER} \
  -Dgithub.repositoryName=${REPOSITORY_NAME} \
  -Dgithub.oauth2=${YOUR_OAUTH2_KEY} \
  -Dfail=false \ # optional, defaults to true. If set to false, build will not break when in low coverage
  -DminCoverage=90 # the minimum acceptable coverage
```

You can get a OAUTH2 key [here](https://github.com/settings/tokens/new).

## Why?

We used to manually look at our code coverage statuses, and see if our
recently added lines are with enough coverage. But this is boring, and
we are developers, and developers automate things, and so we did.

Pull Requests are a part of our culture, and we want good code coverage
to be as well. We already have a [Sonar Plugin][sonar] that will report
code with bugs and other issues, and now we also have Code Coverage.

We configured this plugin to run within our CI Server, so, everything is
automated, we just open a pull request and the CI do the rest.

[sonar]:https://github.com/velo/sonar-pull-request-integration

## How it works

- It's based in Cobertura Maven plugin, but, you this will probably be changed
at some time;
- We use the egit.github library to get and put info into Github using OAUTH2;
- The plugin will map the lines added in your pull request with the results
provided by Cobertura, then calc each chunk coverage, then each file coverage,
and then the overral PR coverage.



