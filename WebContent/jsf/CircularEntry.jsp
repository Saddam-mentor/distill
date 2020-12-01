 <ui:composition
       xmlns="http://www.w3.org/1999/xhtml"
      xmlns:f="http://java.sun.com/jsf/core"
      xmlns:ui="http://java.sun.com/jsf/facelets"
      xmlns:h="http://java.sun.com/jsf/html"
      xmlns:a4j="http://richfaces.org/a4j"
      xmlns:rich="http://richfaces.org/rich">

 <f:view>
 
   <h:form>
   <head>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.0.0/animate.min.css"
	rel="stylesheet" type="text/css"/>

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
.inputtext {
	border-radius: 6px;
	padding: 5px 5px;
	height: 50px;
	width: 600px;
	box-shadow: 1px 1px 4px 4px gray;
	border: 1px solid #669999;
}
.dropdown-menu{
    box-shadow: 1px 1px 2px 3px gray;
}
</style>
			</head>
   <div class="row">
									<rich:spacer height="15px;"></rich:spacer>
								</div>
								<div class="row">
					<a4j:outputPanel id="msg">
						<div class="row col-md-12 wow shake" style="margin-top: 10px;">
							<h:messages errorStyle="color:red"
								style="font-size: 14px;font-weight: bold"
								styleClass="generalExciseStyle" layout="table"
								infoStyle="color:green" />
						</div>
					</a4j:outputPanel>
				</div>

								<div class="row" align="center" style="background-color: #253f8a;">
									<h:outputText value="Portal Circular"
										style="FONT-SIZE: xx-large; FONT-FAMILY: 'Agency FB'; COLOR: #ffffff; TEXT-DECORATION: underline;" />
								</div>
     
                    <div class="row">
									<rich:spacer height="15px;"></rich:spacer>
								</div>
								<rich:separator lineType="dashed"></rich:separator>
								
								 <div class="row">
									<rich:spacer height="10px;"></rich:spacer>
								</div>
								<div align="center">
								<div style="box-shadow: 4px 4px lightsteelblue; border: 2px lightsteelblue solid; width: 98%;">
								<div >
								 <div class="row">
									<rich:spacer height="20px;"></rich:spacer>
								</div>
								<div align="center">
								    Date:-<rich:calendar value="#{circularEntryAction.cir_date}"></rich:calendar>
								</div>
								<rich:spacer height="30px" />
								<div class="row">

								<div class="col-md-6" align="right">
									<h:outputText value="Category List" style="FONT-WEIGHT: bold; "></h:outputText>
								</div>


								<div class="col-md-6" align="left">

									<h:selectOneMenu 
										onchange="this.form.submit();"
										value="#{circularEntryAction.category_id}"
										style="width: 200px; height: 28px;">
										<f:selectItems value="#{circularEntryAction.categoryList}" />
									</h:selectOneMenu>
								</div>

								<div class="row">
									<rich:spacer height="20px;"></rich:spacer>
								</div>
								</div>
								
								<div class="col-md-12">
								  <div class="col-md-3" align="right">
								    <h:outputText value="Heading :"></h:outputText>
								  </div>
								  
								  <div class="col-md-6" align="left" >
								   <h:inputTextarea value="#{circularEntryAction.heading_str }"
												
												styleClass="inputtext">
												<a4j:support event="onblur"
													actionListener="#{circularEntryAction.lengthcheck}"
													reRender="msg"></a4j:support>
											</h:inputTextarea>
								  </div>
								</div>
								<div class="row"><rich:spacer height="20px" /></div>
								<div class="col-md-12">
								  <div class="col-md-3" align="right">
								    <h:outputText value="Circular Discription :"></h:outputText>
								  </div>
								  
								  <div class="col-md-6" align="left" >
								    <h:inputTextarea value="#{circularEntryAction.discription_str}" styleClass="inputtext"></h:inputTextarea>
								  </div>
								</div>
								<div class="row"><rich:spacer height="20px" /></div>
								
								<div class="col-md-12 " align="center">
								<div class="col-md-3" align="right">
								    <h:outputText value="Upload Pdf :"></h:outputText>
								  </div>
								  <div class="col-md-2" align="left">
					<a4j:outputPanel rendered="true">
						<rich:fileUpload listHeight="1px" listWidth="210px"
							fileUploadListener="#{circularEntryAction.doc1uploadMethod}"
							maxFilesQuantity="1" clearControlLabel="Clear"
							clearAllControlLabel="Clear"
							ontyperejected="if (!confirm('Only pdf files are accepted')) return false"
							sizeErrorLabel="" acceptedTypes="pdf" addControlLabel="Add ">
							<a4j:support event="onuploadcomplete"
								reRender="renpdffalse1 , renpdftrue1"></a4j:support>
						</rich:fileUpload>
					</a4j:outputPanel>
				</div>
				

				<div class="col-md-1 img-responsive" align="left">
					<a4j:outputPanel id="renpdftrue1">
						<h:outputLink
							rendered="#{circularEntryAction.doc1upload}"
							target="_blank"
							value="/doc/ExciseUp/pdf/Circular_#{circularEntryAction.srNo}.pdf">

							<h:graphicImage value="/img/download.gif" alt="view document"
								style="width : 60px; height : 35px;"></h:graphicImage>
						</h:outputLink>
					</a4j:outputPanel>
				</div>

				<div class="col-md-1 img-responsive" align="left">
					<a4j:outputPanel id="renpdffalse1">
						<a4j:outputPanel
							rendered="#{!circularEntryAction.doc1upload}">
							<h:graphicImage value="/img/nodoc.png" alt="no document"
								style="width : 60px; height : 35px;"></h:graphicImage>

						</a4j:outputPanel>
					</a4j:outputPanel>

				</div>
				</div>
				<rich:spacer height="30px" />
				<div align="center" class="col-md-12">
				  <h:commandButton value="Save" action="#{circularEntryAction.save}"
				  styleClass="btn btn-success btn-sm"></h:commandButton>
				  <rich:spacer width="5" />
				  <h:commandButton value="Reset" action="#{circularEntryAction.reset}"
				  styleClass="btn btn-warning btn-sm"></h:commandButton>
				</div>
				
				<div><rich:spacer height="20px" /></div>
				
				</div>
				</div>
				</div>
				<div><rich:spacer height="10px" /></div>
				
					<div align="center">

						<rich:dataTable id="table1" rows="10" width="98%" var="list"
						styleClass="table table-hover"
							value="#{circularEntryAction.circular_list}"
							headerClass="TableHead" footerClass="TableHead"
							 rowClasses="TableRow1,TableRow2">
							
							<rich:column>
								<f:facet name="header">
									<h:outputText value="Sr No." style = "color:#ffffff;"
										styleClass="generalExciseStyle"></h:outputText>
								</f:facet>

								<h:outputText value="#{list.srNo_int}" 
									styleClass="generalExciseStyle" ></h:outputText>
							</rich:column>

							<rich:column>
								<f:facet name="header">
									<h:outputText value="Date" style = "color:#ffffff;"
										styleClass="generalExciseStyle"></h:outputText>
								</f:facet>

								<h:outputText value="#{list.date}" styleClass="generalExciseStyle"></h:outputText>
							</rich:column>


							<rich:column>
								<f:facet name="header">
									<h:outputText value="Heading " style = "color:#ffffff;"
										styleClass="generalExciseStyle"></h:outputText>
								</f:facet>
								<h:outputText value="#{list.heading_str_dt}"
									styleClass="generalExciseStyle"></h:outputText>
							</rich:column>
							<rich:column>
								<f:facet name="header">
									<h:outputText value="Discription" style = "color:#ffffff;"
										styleClass="generalExciseStyle"></h:outputText>
								</f:facet>
								<h:outputText value="#{list.discription_str_dt}" styleClass="generalExciseStyle">
									
								</h:outputText>
							</rich:column>
							<rich:column>
								<f:facet name="header">
									<h:outputText value="Category" style = "color:#ffffff;"
										styleClass="generalExciseStyle"></h:outputText>
								</f:facet>
								<h:outputText value="#{list.category_type}" styleClass="generalExciseStyle">
									
								</h:outputText>
							</rich:column>
							
							
							<rich:column >
								<f:facet name="header">
									<h:outputText value="View" style = "color:#ffffff;"></h:outputText>
								</f:facet>
								<a href="https://www.upexciseonline.in//#{list.pdf_str_dt}" target="_blank"><h:outputText
										value="View"></h:outputText></a>

							</rich:column>

							<rich:column >
								<f:facet name="header">
									<h:outputText value=""
										styleClass="generalExciseStyle"></h:outputText>
								</f:facet>
								
				  <center><h:commandButton value="Modify" actionListener="#{circularEntryAction.modify}"
				  styleClass="btn btn-primary btn-sm"></h:commandButton></center>
							</rich:column>
							
							
							<rich:column>
								<f:facet name="header">
									<h:outputText value=""
										styleClass="generalExciseStyle"></h:outputText>
								</f:facet>
								
				  <center><h:commandButton value="Delete" actionListener="#{circularEntryAction.delete}"
				  styleClass="btn btn-danger btn-sm"></h:commandButton></center>
							</rich:column>


							<f:facet name="footer">
								<rich:datascroller for="table1"></rich:datascroller>
							</f:facet>

						</rich:dataTable>

					</div>

					<rich:spacer height="30px" />
					
				<rich:spacer height="30px" />
				
   </h:form>
 </f:view>
</ui:composition>