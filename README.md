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

You can get a OAUTH2 key [here](https://github.com/settings/tokens/new)

## How it works

- It's based in Cobertura Maven plugin, but, you this will probably be changed
at some time;
- We use the egit.github library to get and put info into Github using OAUTH2;
- The plugin will map the lines added in your pull request with the results
provided by Cobertura, then calc each chunk coverage, then each file coverage,
and then the overral PR coverage.



