### Deployment as a Netbeans Project. 

## How to use: 

Open the projet in Netbeans and follow the runtime guide for raspberry pi:
https://blog.idrsolutions.com/2014/08/using-netbeans-remotely-deploy-projects-raspberry-pi/

### Deployment changes: 

File: `remote-platform-impl.xml` 

Add the lines `export DISPLAY=:0.0 ; export SKETCHBOOK=$HOME/sketchbook ;`, to enable the sketch to run on the project.


``` xml 
<target name="-copy-to-remote-platform">
      <macrodef name="runwithpasswd" uri="http://www.netbeans.org/ns/j2se-project/remote-platform/1">
            <attribute name="additionaljvmargs" default=""/>
            <sequential>
                <sshexec host="${remote.platform.host}" port="${remote.platform.port}" username="${remote.platform.user}" password="${remote.platform.password}" trust="true" command="mkdir -p '${remote.dist.dir}'"/>
                <scp todir="${remote.platform.user}@${remote.platform.host}:${remote.dist.dir}" port="${remote.platform.port}" password="${remote.platform.password}" trust="true">
                    <fileset dir="${dist.dir}"/>
                </scp>
                <antcall target="profile-rp-calibrate-passwd"/>
                <sshexec host="${remote.platform.host}" port="${remote.platform.port}" username="${remote.platform.user}" password="${remote.platform.password}" trust="true" usepty="true"
                    command="cd '${remote.project.dir}'; ${remote.platform.exec.prefix}'${remote.java.executable}' @{additionaljvmargs} -Dfile.encoding=${runtime.encoding} ${run.jvmargs} ${run.jvmargs.ide} -jar ${remote.dist.jar} ${application.args}"/>
            </sequential>
        </macrodef>
        <macrodef name="runwithkey" uri="http://www.netbeans.org/ns/j2se-project/remote-platform/1">
            <attribute name="additionaljvmargs" default=""/>
            <sequential>
                <fail unless="remote.platform.keyfile">Must set remote.platform.keyfile</fail>
                <sshexec host="${remote.platform.host}" port="${remote.platform.port}" username="${remote.platform.user}" keyfile="${remote.platform.keyfile}" passphrase="${remote.platform.passphrase}" trust="true" command="mkdir -p '${remote.dist.dir}'"/>
                <scp todir="${remote.platform.user}@${remote.platform.host}:${remote.dist.dir}" port="${remote.platform.port}" keyfile="${remote.platform.keyfile}" passphrase="${remote.platform.passphrase}" trust="true">
                    <fileset dir="${dist.dir}"/>
                </scp>
                <antcall target="profile-rp-calibrate-key"/>
                <sshexec host="${remote.platform.host}" port="${remote.platform.port}" username="${remote.platform.user}" keyfile="${remote.platform.keyfile}" passphrase="${remote.platform.passphrase}" trust="true" usepty="true"
                    command="cd '${remote.project.dir}'; export DISPLAY=:0.0 ; export SKETCHBOOK=$HOME/sketchbook ; ${remote.platform.exec.prefix}'${remote.java.executable}' @{additionaljvmargs} -Dfile.encoding=${runtime.encoding} ${run.jvmargs} ${run.jvmargs.ide} -jar ${remote.dist.jar} ${application.args}"/>
            </sequential>
        </macrodef>
    </target> 
 ```
