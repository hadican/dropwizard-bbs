<#-- @ftlvariable name="" type="com.sony.bbs.dropwizard.view.PresentationView" -->
<html>
    <body>
    	<h3>Topics Presented</h1>
    	<ul>
		    <#list presentations as presentation>
		        <li>${presentation.topic}</li>
			</#list>
    	</ul>
    </body>
</html>