
<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">
	<h:form>
		<f:loadBundle basename="com.mentor.nl.homepagemsg_hi_IN"
			var="msgBundle" />
		<f:view>
			<div class="form-group" style="FONT-FAMILY: 'Arial';">
				<div class="row ">
					<rich:spacer height="15px;" />
				</div>
				<div>
					<a4j:outputPanel id="msg">
						<div class="row wow shake">
							<h:messages errorStyle="color:red"
								style="font-size: 14px;font-weight: bold"
								styleClass="generalExciseStyle" layout="table" id="messages"
								infoStyle="color:green" />
						</div>
					</a4j:outputPanel>
				</div>






				<div class="row " align="center">
					<div class="pghead">
						<h1>
							<h:outputText value="Athenal Movement" />
						</h1>
					</div>
				</div>
				<br /> <br />

				<rich:spacer height="5px" />

				<div align="center" style="BACKGROUND-COLOR: #dee0e2;">

					<div width="100%" align="center">
						<div class="panel-body" align="center">
							<div class="row">
								<div class="col-md-12">
									<div class="col-md-4" align="right">
										<h:outputText value="District"
											style="FONT-FAMILY: 'Times New Roman'; FONT-WEIGHT: bold; FONT-SIZE:20;"></h:outputText>
									</div>
									<div class="col-md-4" align="left">
										<h:selectOneMenu value="#{athenalMovementAction.dist_id}"
											id="id1" styleClass="dropdown-menu " class=" form-control"
											onchange="this.form.submit();">
											<f:selectItems value="#{athenalMovementAction.distList}" />

										</h:selectOneMenu>
									</div>

								</div>

								<div>
									<rich:spacer height="40px"></rich:spacer>
								</div>

								<div class="col-md-12">
									<div align="center">
										<h:selectOneRadio value="#{athenalMovementAction.radio}"
											valueChangeListener="#{athenalMovementAction.chngval}"
											onchange="this.form.submit();">
											<f:selectItem itemValue="A"
												itemLabel="Accepted By Distillery" />
											<f:selectItem itemValue="H"
												itemLabel="Historical (offline Permit)" />
										</h:selectOneRadio>
									</div>
								</div>

								<div class=" row col-md-12">
									<rich:spacer height="20px" />
								</div>

								<div class="row col-md-12" align="center">
									Between Dates :
									<rich:calendar value="#{athenalMovementAction.fromdate}"></rich:calendar>
									and :
									<rich:calendar value="#{athenalMovementAction.todate}"></rich:calendar>
								</div>
								<rich:spacer height="20px" />

							</div>
						</div>

					</div>

					<div class="col-md-12" align="center">


						<h:commandButton action="#{athenalMovementAction.print}"
							value="Print Report"
							style="background-color:#C5C5C5; font-size: large;" />

						<h:outputLink styleClass="outputLinkEx"
							value="/doc/ExciseUp/MIS/pdf/#{athenalMovementAction.pdfname}"
							target="_blank">
							<h:outputText styleClass="outputText" id="text223"
								value="View Report"
								rendered="#{athenalMovementAction.printFlag}"
								style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
						</h:outputLink>



					</div>

				</div>
				<rich:spacer height="20px" />


			</div>
		</f:view>
	</h:form>
</ui:composition>