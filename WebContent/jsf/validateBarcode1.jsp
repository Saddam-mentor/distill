
<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">


	<h:form>
		<f:view>

			<head>
<style>
.button {
	background-color: #b30000;
	border: none;
	color: white;
	padding: 15px 32px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	margin: 4px 2px;
	cursor: pointer;
	border-radius: 4px;
}

.backButton {
	border: none;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	margin: 4px 2px;
	cursor: pointer;
	border-radius: 4px;
	border-radius: 4px;
	background-color: #005ce6;
	color: white;
	padding: 8px 20px;
	color: white;
}

.saveButton {
	border: none;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	margin: 4px 2px;
	cursor: pointer;
	border-radius: 4px;
	border-radius: 4px;
	background-color: #009900;
	color: white;
	padding: 8px 20px;
	background-color: #009900;
	color: white;
}

.inputtext {
	border-radius: 6px;
	background-color: #E8E8E8;
	padding: 5px 5px;
	height: 30px;
	width: 100%;
	box-shadow: 1px 1px 15px lightsteelblue;
	border: 1px solid #669999;
}

.dropdown-menu {
	border-radius: 6px;
	background-color: #E8E8E8;
	padding: 5px 5px;
	height: 30px;
	width: 10%;
	box-shadow: 1px 1px 15px lightsteelblue;
	border: 1px solid #669999;
}

.dropdown-menu1 {
	border-radius: 6px;
	background-color: #E8E8E8;
	padding: 5px 5px;
	height: 30px;
	width: 75%;
	box-shadow: 1px 1px 15px lightsteelblue;
	border: 1px solid #669999;
}
</style>

			</head>



			<div class="row">
				<rich:spacer height="20px;"></rich:spacer>
			</div>

			<div>
				<a4j:outputPanel id="msg">
					<h:messages errorStyle="color:red"
						style="font-size: 14px;font-weight: bold"
						styleClass="generalExciseStyle" layout="table" id="messages"
						infoStyle="color:green">
					</h:messages>
				</a4j:outputPanel>
			</div>


			<a4j:outputPanel id="msg1">
				<div class="col-md-12 wow shake">
					<h:outputText value="#{validateBarcodeAction.errorMsg}"
						style="color: red;"></h:outputText>
					<h:outputText value="#{validateBarcodeAction.succMsg}"
						style="color: green;"></h:outputText>
				</div>
			</a4j:outputPanel>



			<div class="row">
				<rich:spacer height="20px;"></rich:spacer>
			</div>



			<div class="row " align="center">
				<div align="center" width="100%" class="pghead">
					<h2>
						<h:outputText value=" Validate Barcode-N "
							styleClass="generalHeaderOutputTable"
							style="font-weight: bold; font-size: 40px;text-decoration: underline;margin-top: 2px;font-family:Monotype Corsiva;font-size:40px;" />
					</h2>
				</div>
			</div>

			<div class="row">
				<rich:spacer height="30px;"></rich:spacer>
			</div>






			<div class="row">
				<rich:spacer height="10px;"></rich:spacer>
			</div>

			<div class="row col-md-12" align="center"
				style="BACKGROUND-COLOR: #dee0e2;">
				<div class="col-md-12" align="center">
					<h:selectOneRadio
						style="FONT-WEIGHT: bold;  font-size: 18px; width: 20%; "
						value="#{validateBarcodeAction.radio_CL_FL_Beer }"
						valueChangeListener="#{validateBarcodeAction.radioListnr_cl_fl_beer}"
						onchange="this.form.submit();">
						<f:selectItem itemValue="CL" itemLabel="CL" />
						<f:selectItem itemValue="FL" itemLabel="FL" />
						<f:selectItem itemValue="BEER" itemLabel="BEER" />

					</h:selectOneRadio>
				</div>
			</div>

			<div class="row">
				<rich:spacer height="20px;"></rich:spacer>
			</div>
			<div class="row">
				<div class="col-md-4"></div>
				<div class="col-md-1" align="center">
					<h:outputText value="For : " style="FONT-WEIGHT: bold;"
						rendered="#{validateBarcodeAction.fl_Dist_Bwfl2A_Flag or validateBarcodeAction.beer_Brewrey_Bwfl2B_Flag}" />
				</div>
				<div class="col-md-3" align="center">
					<h:selectOneMenu value="#{validateBarcodeAction.fl_Dist_Bwfl2A}"
						onchange="this.form.submit();"
						valueChangeListener="#{validateBarcodeAction.list_clr_Listnr}"
						rendered="#{validateBarcodeAction.fl_Dist_Bwfl2A_Flag}"
						styleClass="dropdown-menu1" style="FONT-FAMILY: 'Bahnschrift';">
						<f:selectItem itemValue="0" itemLabel="--Select--" />
						<f:selectItem itemValue="Dist_FL" itemLabel="Distillery" />
						<f:selectItem itemValue="BWFL2A" itemLabel="BWFL2A" />
					</h:selectOneMenu>

					<h:selectOneMenu
						value="#{validateBarcodeAction.beer_Brewrey_Bwfl2B}"
						onchange="this.form.submit();"
						valueChangeListener="#{validateBarcodeAction.list_clr_Listnr2}"
						rendered="#{validateBarcodeAction.beer_Brewrey_Bwfl2B_Flag}"
						styleClass="dropdown-menu1" style="FONT-FAMILY: 'Bahnschrift';">
						<f:selectItem itemValue="" itemLabel="--Select--" />
						<f:selectItem itemValue="Brewery" itemLabel="Brewery" />
						<f:selectItem itemValue="BWFL2B" itemLabel="BWFL2B" />
					</h:selectOneMenu>

				</div>

				<div class="col-md-4"></div>
			</div>

			<div class="row">
				<rich:spacer height="20px;"></rich:spacer>
			</div>

			<div class="row">
				<div class="col-md-4"></div>
				<div class="col-md-1" align="center">
					<h:outputText value="List  : " style="FONT-WEIGHT: bold;"
						rendered="#{validateBarcodeAction.dist_Bwfl_brwryList_Flag }" />
				</div>
				<div class="col-md-3" align="center">
					<h:selectOneMenu
						value="#{validateBarcodeAction.dist_Bwfl_brwryId }"
						rendered="#{validateBarcodeAction.dist_Bwfl_brwryList_Flag }"
						onchange="this.form.submit();"
						valueChangeListener="#{validateBarcodeAction.showdta_Listnr}"
						styleClass="dropdown-menu1"
						style="width:250px ; FONT-FAMILY: 'Bahnschrift'; ">

						<f:selectItems
							value="#{validateBarcodeAction.dist_Bwfl_brwryList}" />
					</h:selectOneMenu>
				</div>
				<div class="col-md-4"></div>
			</div>




			<div class="row">
				<rich:spacer height="10px;"></rich:spacer>
			</div>
			<div class="row">
				<div class="col-md-4"></div>
				<div class="col-md-1" align="center">
					<h:outputText value="For : " style="FONT-WEIGHT: bold;"
						rendered="false" />
				</div>
				<div class="col-md-3" align="center">
					<h:selectOneMenu value="#{validateBarcodeAction.type}"
						styleClass="dropdown-menu1" style="FONT-FAMILY: 'Bahnschrift';"
						rendered="false">
						<f:selectItem itemValue="" itemLabel="--Select--" />
						<f:selectItem itemValue="D" itemLabel="Distillery" />
						<f:selectItem itemValue="B" itemLabel="Brewery" />
					</h:selectOneMenu>
				</div>
				<div class="col-md-4"></div>
			</div>

			<div class="row">
				<rich:spacer height="20px;"></rich:spacer>
			</div>
			
			
			<div class="row" align="center"  style="BACKGROUND-COLOR: #dee0e2;">
			<h:selectOneRadio style="FONT-WEIGHT: bold;  font-size: 18px; width: 20%; "
									
									value="#{validateBarcodeAction.radioExcl_Csv}"
									onchange="this.form.submit()">
									<f:selectItem itemValue="E" itemLabel="Upload Excel" />
									<f:selectItem itemValue="C" itemLabel="Upload CSV" />

								</h:selectOneRadio>
			</div>
			
			

			<div class="row">

				<div class="row">
					<div class="col-md-4"></div>

					<div class="col-md-1" align="center">
						<h:outputText value="Upload File: " style="FONT-WEIGHT: bold;" 
						rendered="#{ validateBarcodeAction.radioExcl_Csv eq 'E' and validateBarcodeAction.radioExcl_Csv eq 'C' }" />
					</div>
					<div class="col-md-3" align="center">

						<rich:fileUpload addControlLabel="Add File" id="link3"
							fileUploadListener="#{validateBarcodeAction.uploadExcel}"
							rendered="#{ validateBarcodeAction.radioExcl_Csv eq 'E'}"
							maxFilesQuantity="1" listHeight="40" listWidth="250"
							clearControlLabel="clear" stopControlLabel="Stop"
							ontyperejected="if (!confirm(' ONLY .xlsx type file ALLOWED !!!')) return false"
							acceptedTypes="xlsx" clearAllControlLabel="Clear">
						</rich:fileUpload>
						
						
						<rich:spacer height="10px"/>
						
						<rich:fileUpload addControlLabel="Add File CSV" id="link34"
									fileUploadListener="#{validateBarcodeAction.uploadCsv}"
									rendered="#{ validateBarcodeAction.radioExcl_Csv eq 'C'}"
									maxFilesQuantity="1" listHeight="40" listWidth="250"
									clearControlLabel="clear" stopControlLabel="Stop"
									ontyperejected="if (!confirm(' ONLY .csv type file ALLOWED !!!')) return false"
									acceptedTypes="csv" clearAllControlLabel="Clear">
								</rich:fileUpload>
								
						

					</div>

					<div class="col-md-2"></div>

					<div class="col-md-4" align="center"></div>
				</div>



				<div class="row">
					<rich:spacer height="20px" />
				</div>

				<div class="panel-footer clearfix">
					<div class="row" align="center">
						<div class="col-md-12" align="center">
							<h:commandButton value="Submit Excel" styleClass="btn btn-primary"
								action="#{validateBarcodeAction.importExcel}" 
								rendered="#{ validateBarcodeAction.radioExcl_Csv eq 'E'}">
							</h:commandButton>

							<rich:spacer width="20px" />

							<h:commandButton value="Reset" styleClass="btn btn-warning"
								action="#{validateBarcodeAction.reset}">
							</h:commandButton>
							
							<rich:spacer width="20px" />
							
							<h:commandButton value="Upload CSV"
									styleClass="btn btn-primary"
									rendered="#{ validateBarcodeAction.radioExcl_Csv eq 'C'}"
									action="#{validateBarcodeAction.csvSubmit }">
								</h:commandButton>
							
						</div>
					</div>
				</div>






				<div class="row">
					<rich:spacer height="20px;"></rich:spacer>
				</div>



				<div class="row" align="center">

					<div style="width: 1450px; overflow-x: scroll;">

						<rich:dataTable columnClasses="columnClass1"
							headerClass="TableHead" rowClasses="TableRow1,TableRow2"
							styleClass="DataTable" id="table2" width="100%"
							value="#{validateBarcodeAction.showdata}" var="list">
							<rich:column>
								<f:facet name="header">
									<h:outputText value="Seq"
										style="FONT-FAMILY: 'Times New Roman'; FONT-WEIGHT: bold; FONT-SIZE: small;">
									</h:outputText>
								</f:facet>
								<h:outputText value="#{list.seq}"
									styleClass="generalHeaderStyleOutput">

								</h:outputText>
							</rich:column>



                         <rich:column>
								<f:facet name="header">
									<h:outputText value="Uploaded Excel Id"
										style="FONT-FAMILY: 'Times New Roman'; FONT-WEIGHT: bold; FONT-SIZE: small;">
									</h:outputText>
								</f:facet>
								<h:outputText value="#{list.uploadedExcelId}"
									styleClass="generalHeaderStyleOutput">

								</h:outputText>
							</rich:column>




							<rich:column>
								<f:facet name="header">
									<h:outputText value="Date"
										style="FONT-FAMILY: 'Times New Roman'; FONT-WEIGHT: bold; FONT-SIZE: small;">
									</h:outputText>
								</f:facet>
								<h:outputText value="#{list.cr_date}"
									styleClass="generalHeaderStyleOutput">
									

								</h:outputText>
							</rich:column>


							<rich:column>
								<f:facet name="header">
									<h:outputText value="Type"
										style="FONT-FAMILY: 'Times New Roman'; FONT-WEIGHT: bold; FONT-SIZE: small;">
									</h:outputText>
								</f:facet>
								<h:outputText value="#{list.type}"
									styleClass="generalHeaderStyleOutput">

								</h:outputText>
							</rich:column>

							<rich:column>
								<f:facet name="header">
								</f:facet>
								<center>
									<h:commandButton styleClass="btn btn-success"
										value="Print BarCode"
										actionListener="#{validateBarcodeAction.printDetail}">
									</h:commandButton>

									<h:outputLink styleClass="outputLinkEx"
										value="/doc/ExciseUp/WholeSale/pdf/#{validateBarcodeAction.pdfName}"
										target="_blank">
										<h:outputText styleClass="outputText" id="text225"
											value="View Report" rendered="#{list.printFlag}"
											style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
									</h:outputLink>

								</center>
							</rich:column>

							<rich:column>
								<f:facet name="header">
									<h:outputText value="Barcode"
										style="FONT-FAMILY: 'Times New Roman'; FONT-WEIGHT: bold; FONT-SIZE: small;">
									</h:outputText>
								</f:facet>
								<h:outputText value="#{list.barcode}"
									styleClass="generalHeaderStyleOutput">

								</h:outputText>
							</rich:column>

						</rich:dataTable>
					</div>
				</div>


				<div class="row">
					<rich:spacer height="20px;"></rich:spacer>
				</div>

				<div class="row" align="center">
					<h:commandButton value="Print Report" styleClass="btn btn-success"
						action="#{validateBarcodeAction.print}" rendered="false">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/WholeSale/pdf/#{validateBarcodeAction.pdfName}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{validateBarcodeAction.printFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>


				</div>




			</div>

               

			<div class="row">
				<rich:spacer height="20px;"></rich:spacer>
			</div>
			
			
			<rich:dataTable width="100%" var="list" value="#{validateBarcodeAction.datatable}" columnClasses="columnClass1"
							headerClass="TableHead" rowClasses="TableRow1,TableRow2"
							styleClass="DataTable" id="table3" >
								
								
								<rich:column>
								<f:facet name="header">
								<h:outputText value="Uploaded ID" />
								</f:facet>
								<h:outputText value="#{list.uploadedId}" />
								</rich:column>
								
								
								
								<rich:column>
								<f:facet name="header">
								<h:outputText value="Accepted Bar CodeCount" />
								</f:facet>
								<h:outputText value="#{list.barCodeCount}" />
								</rich:column>
								
								
								
								
								<rich:column>
								<f:facet name="header">
								<h:outputText value="Type" />
								</f:facet>
								<h:outputText value="#{list.type}" />
								</rich:column>
								
								
								
									<rich:column>
								<f:facet name="header">
								<h:outputText value="Date" />
								</f:facet>
								<h:outputText value="#{list.date}">
								<f:convertDateTime pattern = "dd-mm-yyyy" />
								</h:outputText>
								</rich:column>
								
								<rich:column>
								<f:facet name="header">
								<h:outputText value="Status" />
								</f:facet>
								<h:outputText value="#{list.status}" />
								</rich:column>
								
								
								
								<rich:column>
								<f:facet name="header">
								<h:outputText value="Finalize" />
								</f:facet>
								<h:commandButton action="#{validateBarcodeAction.finalizeData}" value="Finalize" disabled="#{list.finalizeFlag eq 'F'}"  onclick="var e=this;setTimeout(function(){e.disabled=true;},0);return true;" styleClass="btn btn-danger">
								
								<f:setPropertyActionListener target="#{validateBarcodeAction.dt}" value="#{list}" />
								
								</h:commandButton>
								
								
								
								
								<h:commandButton action="#{validateBarcodeAction.generateExcel}" value="Generate Excel" onclick="var e=this;setTimeout(function(){e.disabled=true;},0);return true;" disabled="#{list.finalizeFlag ne 'F'}" styleClass="btn btn-primary">
								
								<f:setPropertyActionListener target="#{validateBarcodeAction.dt}" value="#{list}" />
								
								</h:commandButton>
								
								
								<h:outputLink target="_blank" value="/doc/ExciseUp/Excel/NewBarcode#{list.uploadedId}#{list.datelink}.xls">
															<h:outputText value="Download Excel" rendered="#{list.finalizeFlag eq 'F'}"/>
															
															</h:outputLink> 
								</rich:column>
								
								</rich:dataTable> 
								
			
			
			
		</f:view>
	</h:form>
</ui:composition>