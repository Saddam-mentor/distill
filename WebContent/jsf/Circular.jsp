
<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">
	



	<f:view>


		<h:form style="FONT-SIZE: large;">
			<head>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.0.0/animate.min.css"
	rel="stylesheet" type="text/css">
</link>
<style rel="stylesheet" type="text/css">
.text {
	font-weight: 700;
	font-size: 40px;
	color: #f5f5f5;
	text-shadow: 1px 1px 1px #919191, 1px 2px 1px #919191, 1px 3px 1px
		#919191, 1px 4px 1px #919191, 1px 5px 1px #919191, 1px 6px 1px #919191,
		1px 7px 1px #919191, 1px 8px 1px #919191, 1px 9px 1px #919191, 1px
		10px 1px #919191, 1px 18px 6px rgba(16, 16, 16, 0.4), 1px 22px 10px
		rgba(16, 16, 16, 0.2), 1px 25px 35px rgba(16, 16, 16, 0.2), 1px 30px
		60px rgba(16, 16, 16, 0.4);
}

.table {
	background-color: #e6f9ff;
}

.TableHead {
	background-color: #253f8a;
}
</style>
			</head>
			<section style="background-color: #f7f8fa; color: #3f4c56">

				<div class="row">
					<div class="row " align="center">
						<div class="col-md-2" align="center"></div>
						<div class="col-md-10" align="left">
							<h:messages errorStyle="color:red"
								style="FONT-STYLE: italic; font-size: 26; margin-top: 15px;"
								styleClass="generalExciseStyle" layout="table" id="messages"
								infoStyle="color:green">
							</h:messages>
							
						</div>
					</div>
					<div class="row">
						<rich:spacer height="20px;"></rich:spacer>
					</div>
					<div style="background-color: #253f8a;" class="col-md-12 col-sm-12  col-xs-12  ">

						<div class="col-md-1 hidden-sm  hidden-xs "></div>

						<div class="col-md-10 col-sm-12  col-xs-12 ">



							<h2 style="COLOR: #ffffff; text-align: center;">Portal
								Circulars</h2>



						</div>
						<div class="col-md-1 col-sm-12  col-xs-12  " align="center">
							<a href="/portal/Home" style="COLOR: #ffffff; text-align: center;"><b>BACK</b> </a>
						</div>
					</div>
					<div class="row">
						<rich:spacer height="20px;"></rich:spacer>
					</div>
					<div align="center">
						<div
							style="box-shadow: 4px 4px lightsteelblue; border: 2px lightsteelblue solid; width: 98%;">
							<div class="row ">
								<rich:spacer height="20px" />
							</div>
							<div class="col-md-12" align="center">
								<h:selectOneRadio value="#{circularAction.radio}"
									valueChangeListener="#{circularAction.datalist}"
									onchange="this.form.submit();">

									<f:selectItem itemValue="A" itemLabel="All" />
									<f:selectItem itemValue="C" itemLabel="Categorywise" />
								</h:selectOneRadio>
							</div>

							<div class="row">
								<rich:spacer height="20px;"></rich:spacer>
							</div>

							<div class="row">

								<div class="col-md-6" align="right">
									<h:outputText value="Category List"
										rendered="#{circularAction.radio eq 'C'}"
										style="FONT-WEIGHT: bold; "></h:outputText>
								</div>


								<div class="col-md-6" align="left">

									<h:selectOneMenu rendered="#{circularAction.radio eq 'C'}"
										onchange="this.form.submit();"
										value="#{circularAction.category_id}"
										style="width: 200px; height: 28px;">
										<f:selectItems value="#{circularAction.categoryList}" />
									</h:selectOneMenu>
								</div>

								<div class="row">
									<rich:spacer height="20px;"></rich:spacer>
								</div>

								<div class="row col-md-12" align="center">
									From Date :
									<rich:calendar 
										value="#{circularAction.fromdate}"></rich:calendar>
									<rich:spacer width="50px" />
									To Date :
									<rich:calendar value="#{circularAction.todate}"></rich:calendar>

								</div>



								<div class="row ">
									<rich:spacer height="30px" />
								</div>




							</div>
							<div class="col-md-12" align="center">
								<h:commandButton action="#{circularAction.datalist1}"
									value="Get Detail" class="btn btn-primary"></h:commandButton>
							</div>
							<div class="row ">
								<rich:spacer height="20px" />
							</div>
						</div>

					</div>
					<div class="row ">
						<rich:spacer height="20px" />
					</div>

					<div class="row" align="center">
						<rich:dataTable id="table3" rows="10" width="80%"
							styleClass="table table-hover" value="#{circularAction.news}"
							headerClass="TableHead" footerClass="TableHead" 
							var="list" rowClasses="TableRow1,TableRow2">

							<rich:column sortBy="#{list.newsserial }" align="center">
								<f:facet name="header">
									<h:outputText value="S.No" style = "color:#ffffff;"></h:outputText>
								</f:facet>
								<h:outputText value="#{list.newsserial }"
									styleClass="generalExciseStyle"></h:outputText>
							</rich:column>

							<rich:column sortBy="#{list.newsdt}" filterBy="#{list.newsdt}">
								<f:facet name="header">
									<h:outputText value="Date " style = "color:#ffffff;">
									</h:outputText>
								</f:facet>

								<h:outputText styleClass="generalExciseStyle"
									value="#{list.newsdt}">
									<f:convertDateTime pattern="dd-M-yyyy" timeZone="GMT+05:30" />
								</h:outputText>
							</rich:column>


							<rich:column sortBy="#{list.newshead}" align="center"
								filterBy="#{list.newshead}">
								<f:facet name="header">
									<h:outputText value="Circular" style = "color:#ffffff;">
									</h:outputText>
								</f:facet>
								<h:outputText value="#{list.newshead}"
									styleClass="generalExciseStyle">
									<f:convertDateTime pattern="dd/MM/yyyy" timeZone="GMT+5:30" />
								</h:outputText>
							</rich:column>

							<rich:column sortBy="#{list.newsdesc}"
								filterBy="#{list.newsdesc }">
								<f:facet name="header">
									<h:outputText value="Description" style = "color:#ffffff;"></h:outputText>
								</f:facet>
								<h:outputText value="#{list.newsdesc}"
									styleClass="generalExciseStyle"></h:outputText>
							</rich:column>



							<rich:column sortBy="#{list.newsfile}"
								filterBy="#{list.newsfile}">
								<f:facet name="header">
									<h:outputText value=" Download pdf" style = "color:#ffffff;"></h:outputText>
								</f:facet>
								<a href="https://www.upexciseonline.in/#{list.newsfile}" target="_blank"><h:outputText
										value="Download"></h:outputText></a>

							</rich:column>









							<f:facet name="footer">
								<rich:datascroller for="table3"></rich:datascroller>
							</f:facet>
						</rich:dataTable>
					</div>
					<div class="row ">
						<rich:spacer height="30px" />
					</div>

				</div>

			</section>








		</h:form>

	</f:view>

</ui:composition>