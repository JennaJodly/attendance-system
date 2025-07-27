<template>
  <div class="ma-6">
    <v-card elevation="5" class="px-6">
      <v-row class="d-flex justify-space-between align-center mb-2 pt-5">
        <h2>Add Employee Details</h2>
        <v-btn color="primary" size="small" @click="goback">Back</v-btn>
      </v-row>

      <v-form ref="mainForm" @submit.prevent="submitForm">
        <v-row dense wrap>
          <!-- Name Fields -->
          <v-col cols="12" sm="6" md="3">
            <v-text-field
              label="Employee First Name *"
              v-model="form.firstName"
              :rules="[rules.required]"
              required
            />
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-text-field label="Employee Last Name" v-model="form.lastName" />
          </v-col>

          <!-- Employee Details -->
          <v-col cols="12" sm="6" md="3">
            <v-text-field
              label="Username"
              v-model="form.username"
              hint="Min 5 & Max 15 characters"
              :rules="[rules.minMaxLength(5, 15)]"
            />
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-text-field 
              label="Employee No *" 
              v-model="form.empNo" 
              :rules="[rules.required]"
              required 
            />
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-text-field 
              label="Email *" 
              v-model="form.email" 
              :rules="[rules.required, rules.email]"
              required 
            />
          </v-col>

          <!-- Address -->
          <v-col cols="12" sm="6" md="3">
            <v-text-field label="Address Line 1" v-model="form.address1" />
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-text-field label="Address Line 2" v-model="form.address2" />
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-autocomplete
              v-model="form.country"
              :items="countries"
              item-title="name"
              item-value="code"
              label="Select Country"
              return-object
              @update:model-value="loadStates"
            ></v-autocomplete>
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-autocomplete
              v-model="form.state"
              :items="states"
              label="State"
              item-title="name"
              item-value="code"
              return-object
              @update:model-value="loadCities"
            />
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-autocomplete
              v-model="form.city"
              :items="cities"
              label="City"
              item-title="name"
              item-value="code"
              return-object
            />
          </v-col>

          <!-- Contact Info -->
          <v-col cols="12" sm="6" md="3">
            <v-text-field 
              label="Official No 1" 
              v-model="form.officialNo"
              :rules="[rules.phone]"
            />
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-text-field 
              label="WhatsApp No" 
              v-model="form.whatsappNo"
              :rules="[rules.phone]"
            />
          </v-col>

          <!-- Important Dates -->
          <v-col cols="12" sm="6" md="3">
            <v-text-field
              label="Birthday Date"
              v-model="form.bdayDate"
              type="date"
            />
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-text-field
              label="Date of Joining"
              v-model="form.dateOfJoining"
              type="date"
            />
          </v-col>

          <!-- Other Details -->
          <v-col cols="12" sm="6" md="3">
            <v-autocomplete
              label="Gender"
              :items="['Male', 'Female', 'Other']"
              v-model="form.gender"
            />
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-autocomplete
              label="Blood Group"
              :items="['A+', 'A-', 'B+', 'B-', 'O+', 'O-', 'AB+', 'AB-']"
              v-model="form.bloodGroup"
            />
          </v-col>

          <!-- Emergency Contact -->
          <v-col cols="12" sm="6" md="3">
            <v-text-field
              label="Emergency Contact No"
              v-model="form.emergencyContactNo"
              :rules="[rules.phone]"
            />
          </v-col>

          <!-- Photo Upload -->
          <v-col cols="12" sm="6" md="3" class="d-flex justify-center">
            <v-avatar size="50">
              <v-img :src="form.photo || defaultAvatar" alt="Profile" />
            </v-avatar>
           <v-file-input
              class="ml-4"
              accept="image/*"
              label="Upload Photo"
              :multiple="false"
              @update:model-value="handlePhotoUpload"
            />
          </v-col>
          
          <!-- Fingerprint Scan -->
          <v-col cols="12" sm="6" md="3" class="d-flex flex-column align-center justify-center">
            <v-icon size="40" color="primary">fingerprint</v-icon>
            <v-btn
              color="primary"
              class="mt-2"
              @click="scanFingerprint"
              :loading="scanning"
            >
              Scan Fingerprint
            </v-btn>
            <v-chip v-if="form.fingerprint" class="mt-2" color="success" label>
              Fingerprint Captured
            </v-chip>
          </v-col>
        </v-row>
      </v-form>
    </v-card>

    <!-- Tabs Section -->
    <v-card elevation="2" class="pa-6 mt-6">
      <v-tabs v-model="tab" background-color="transparent" color="primary">
        <v-tab v-for="(item, i) in tabItems" :key="i">
          {{ item.label }}
        </v-tab>
      </v-tabs>
      <v-window v-model="tab">
        <v-window-item v-for="(item, i) in tabItems" :key="i">
          <div class="pa-4">
            <!-- Other Details Tab -->
            <template v-if="tab === 0">
              <v-form ref="otherDetailsForm">
                <v-row dense>
                  <!-- Branch -->
                  <v-col cols="12" sm="6" md="3">
                    <div class="d-flex align-center">
                      <v-autocomplete
                        label="Branch *"
                        :items="branches"
                        item-title="name"
                        item-value="id"
                        v-model="otherDetails.branch"
                        return-object
                        :rules="[rules.required]"
                        required
                      />
                      <v-btn
                        icon
                        color="primary"
                        class="ml-2"
                        @click="addBranch"
                        density="compact"
                        style="height: 28px; width: 28px; min-width: 28px"
                      >
                        <v-icon size="18">mdi-plus</v-icon>
                      </v-btn>
                    </div>
                  </v-col>

                  <!-- Department -->
                  <v-col cols="12" sm="6" md="3">
                    <div class="d-flex align-center">
                      <v-autocomplete
                        label="Department *"
                        :items="departments"
                        item-title="name"
                        item-value="id"
                        v-model="otherDetails.department"
                        return-object
                        @update:model-value="handleDepartmentChange"
                        :rules="[rules.required]"
                        required
                      />
                      <v-btn
                        icon
                        color="primary"
                        class="ml-2"
                        @click="addDepartment"
                        density="compact"
                        style="height: 28px; width: 28px; min-width: 28px"
                      >
                        <v-icon size="18">mdi-plus</v-icon>
                      </v-btn>
                    </div>
                  </v-col>

                  <!-- Sub Department -->
                  <v-col cols="12" sm="6" md="3">
                    <div class="d-flex align-center">
                      <v-autocomplete
                        label="Sub Department"
                        :items="subDepartments"
                        item-title="name"
                        item-value="id"
                        v-model="otherDetails.subDepartment"
                        return-object
                        :disabled="!otherDetails.department"
                      />
                      <v-btn
                        icon
                        color="primary"
                        class="ml-2"
                        @click="addSubDepartment"
                        density="compact"
                        style="height: 28px; width: 28px; min-width: 28px"
                      >
                        <v-icon size="18">mdi-plus</v-icon>
                      </v-btn>
                    </div>
                  </v-col>

                  <!-- Designation -->
                  <v-col cols="12" sm="6" md="3">
                    <div class="d-flex align-center">
                      <v-autocomplete
                        label="Designation *"
                        :items="designations"
                        item-title="name"
                        item-value="id"
                        v-model="otherDetails.designation"
                        return-object
                        :rules="[rules.required]"
                        required
                      />
                      <v-btn
                        icon
                        color="primary"
                        class="ml-2"
                        @click="addDesignation"
                        density="compact"
                        style="height: 28px; width: 28px; min-width: 28px"
                      >
                        <v-icon size="18">mdi-plus</v-icon>
                      </v-btn>
                    </div>
                  </v-col>

                  <!-- Region -->
                  <v-col cols="12" sm="6" md="3">
                    <div class="d-flex align-center">
                      <v-autocomplete
                        label="Region"
                        :items="regions"
                        item-title="name"
                        item-value="id"
                        v-model="otherDetails.region"
                        return-object
                      />
                      <v-btn
                        icon
                        color="primary"
                        class="ml-2"
                        @click="addRegion"
                        density="compact"
                        style="height: 28px; width: 28px; min-width: 28px"
                      >
                        <v-icon size="18">mdi-plus</v-icon>
                      </v-btn>
                    </div>
                  </v-col>

                  <!-- Channel/Division -->
                  <v-col cols="12" sm="6" md="3">
                    <div class="d-flex align-center">
                      <v-autocomplete
                        label="Channel/Division"
                        :items="divisions"
                        item-title="name"
                        item-value="id"
                        v-model="otherDetails.division"
                        return-object
                        @update:model-value="handleDivisionChange"
                      />
                      <v-btn
                        icon
                        color="primary"
                        class="ml-2"
                        @click="addDivision"
                        density="compact"
                        style="height: 28px; width: 28px; min-width: 28px"
                      >
                        <v-icon size="18">mdi-plus</v-icon>
                      </v-btn>
                    </div>
                  </v-col>

                  <!-- Sub Channel/Sub Division -->
                  <v-col cols="12" sm="6" md="3">
                    <div class="d-flex align-center">
                      <v-autocomplete
                        label="Sub Channel/Sub Division"
                        :items="subDivisions"
                        item-title="name"
                        item-value="id"
                        v-model="otherDetails.subChannel"
                        return-object
                        :disabled="!otherDetails.division"
                      />
                      <v-btn
                        icon
                        color="primary"
                        class="ml-2"
                        @click="addSubDivision"
                        density="compact"
                        style="height: 28px; width: 28px; min-width: 28px"
                      >
                        <v-icon size="18">mdi-plus</v-icon>
                      </v-btn>
                    </div>
                  </v-col>

                  <!-- Category/Product -->
                  <v-col cols="12" sm="6" md="3">
                    <div class="d-flex align-center">
                      <v-autocomplete
                        label="Category/Product"
                        :items="categories"
                        item-title="name"
                        item-value="id"
                        v-model="otherDetails.categoryProduct"
                        return-object
                      />
                      <v-btn
                        icon
                        color="primary"
                        class="ml-2"
                        @click="addCategoryProduct"
                        density="compact"
                        style="height: 28px; width: 28px; min-width: 28px"
                      >
                        <v-icon size="18">mdi-plus</v-icon>
                      </v-btn>
                    </div>
                  </v-col>

                  <!-- Employee Grade -->
                  <v-col cols="12" sm="6" md="3">
                    <div class="d-flex align-center">
                      <v-autocomplete
                        label="Employee Grade"
                        :items="employeeGrades"
                        item-title="name"
                        item-value="id"
                        v-model="otherDetails.employeeGrade"
                        return-object
                      />
                      <v-btn
                        icon
                        color="primary"
                        class="ml-2"
                        @click="addEmployeeGrade"
                        density="compact"
                        style="height: 28px; width: 28px; min-width: 28px"
                      >
                        <v-icon size="18">mdi-plus</v-icon>
                      </v-btn>
                    </div>
                  </v-col>

                  <!-- Reporting To -->
                  <v-col cols="12" sm="6" md="3">
                    <v-autocomplete
                      label="Reporting To *"
                      :items="employee_list"
                      v-model="otherDetails.reportingTo"
                      item-title="name"
                      item-value="id"
                      return-object
                      :rules="[rules.required]"
                      required
                    />
                  </v-col>
                  
                  <!-- Number of Appraisers -->
                  <v-col cols="12" sm="6" md="3">
                    <v-select
                      label="No of Appraisers *"
                      :items="[1, 2]"
                      v-model="otherDetails.noOfAppraisers"
                      :rules="[rules.required]"
                      required
                    />
                  </v-col>
                  
                  <!-- Appraiser 1 -->
                  <v-col cols="12" sm="6" md="3" v-if="otherDetails.noOfAppraisers >= 1">
                    <v-autocomplete
                      label="Appraiser 1 *"
                      :items="employee_list"
                      v-model="otherDetails.appraiser1"
                      item-title="name"
                      item-value="id"
                      return-object
                      :rules="otherDetails.noOfAppraisers >= 1 ? [rules.required] : []"
                    />
                  </v-col>
                  
                  <!-- Appraiser 2 -->
                  <v-col cols="12" sm="6" md="3" v-if="otherDetails.noOfAppraisers == 2">
                    <v-autocomplete
                      label="Appraiser 2 *"
                      :items="employee_list"
                      item-title="name"
                      item-value="id"
                      return-object   
                      v-model="otherDetails.appraiser2"
                      :rules="otherDetails.noOfAppraisers == 2 ? [rules.required] : []"
                    />
                  </v-col>
                  
                  <!-- Reviewer -->
                  <v-col cols="12" sm="6" md="3">
                    <v-autocomplete 
                      label="Reviewer" 
                      :items="employee_list" 
                      item-title="name"
                      item-value="id" 
                      v-model="otherDetails.reviewer" 
                    />
                  </v-col>
                  
                  <!-- Effective From -->
                  <v-col cols="12" sm="6" md="3">
                    <v-text-field 
                      label="Effective From" 
                      v-model="otherDetails.effectiveFrom" 
                      type="date" 
                    />
                  </v-col>
                </v-row>
                <v-row>
                    <v-col cols="12" style="text-align: right;">
                        <v-btn color="primary" size="small" class="mt-4" @click="validateAndGoToNextTab">Next</v-btn>
                    </v-col>
                </v-row>
              </v-form>
            </template>

            <!-- Documents Tab -->
            <template v-else-if="tab === 1">
              <v-form ref="documentsForm">
                <v-row
                  dense
                  v-for="(doc, idx) in documents"
                  :key="idx"
                  class="align-center mb-2"
                >
                  <v-col cols="12" sm="3">
                    <v-text-field
                      label="Document Name"
                      v-model="doc.name"
                      dense
                    />
                  </v-col>
                  <v-col cols="12" sm="2">
                    <v-text-field
                      label="Document No"
                      v-model="doc.number"
                      dense
                    />
                  </v-col>
                  <v-col cols="12" sm="2">
                    <v-text-field
                      label="Start from"
                      v-model="doc.start"
                      type="date"
                      dense
                    />
                  </v-col>
                  <v-col cols="12" sm="2">
                    <v-text-field
                      label="Expiry date"
                      v-model="doc.expiry"
                      type="date"
                      dense
                    />
                  </v-col>
                 <v-col cols="12" sm="1">
                    <input
                      type="file"
                      :ref="'fileInput' + idx"
                      style="display: none"
                      @change="onFileSelected($event, idx)"
                      accept=".pdf,image/*"
                    />
                    <v-btn icon @click="triggerFileInput(idx)">
                      <v-icon>mdi-upload</v-icon>
                    </v-btn>
                  </v-col>

                  <v-col cols="12" sm="2" class="d-flex align-center">
                    <v-btn
                      icon
                      color="success"
                      class="mr-2"
                      density="compact"
                      size="small"
                      @click="addDocumentRow"
                      v-if="idx === documents.length - 1"
                    >
                      <v-icon size="18">mdi-plus-circle</v-icon>
                    </v-btn>
                    <v-btn
                      icon
                      color="error"
                      density="compact"
                      size="small"
                      @click="removeDocumentRow(idx)"
                      v-if="documents.length > 1"
                    >
                      <v-icon size="18">mdi-minus-circle</v-icon>
                    </v-btn>
                  </v-col>
                </v-row>
                <v-row style="justify-content: space-between">
                  <v-col>
                    <v-btn
                      color="primary"
                      size="small"
                      class="mt-4"
                      @click="goToPrevTab"
                      >Previous</v-btn
                    >
                  </v-col>
                  <v-col style="text-align: right">
                    <v-btn
                      color="primary"
                      size="small"
                      class="mt-4 ml-2"
                      @click="goToNextTab"
                      >Next</v-btn
                    >
                  </v-col>
                </v-row>
              </v-form>
            </template>
            
            <!-- Bank Details Tab (last tab) -->
            <template v-else-if="tab === 2">
              <v-form ref="bankDetailsForm" @submit.prevent="validateAndSubmitForm">
                <v-row
                  dense
                  v-for="(bank, idx) in bankDetails"
                  :key="idx"
                  class="align-center mb-2"
                >
                  <v-col cols="12" sm="3">
                    <v-text-field
                      label="Bank Name"
                      v-model="bank.bankName"
                      dense
                    />
                  </v-col>
                  <v-col cols="12" sm="3">
                    <v-text-field
                      label="Account No"
                      v-model="bank.accountNo"
                      dense
                    />
                  </v-col>
                  <v-col cols="12" sm="3">
                    <v-text-field label="IFSC code" v-model="bank.ifsc" dense />
                  </v-col>
                  <v-col cols="12" sm="1" class="d-flex align-center">
                    <v-btn
                      icon
                      color="success"
                      class="mr-2"
                      density="compact"
                      size="small"
                      @click="addBankRow"
                      v-if="idx === bankDetails.length - 1"
                    >
                      <v-icon size="18">mdi-plus-circle</v-icon>
                    </v-btn>
                    <v-btn
                      icon
                      color="error"
                      density="compact"
                      size="small"
                      @click="removeBankRow(idx)"
                      v-if="bankDetails.length > 1"
                    >
                      <v-icon size="18">mdi-minus-circle</v-icon>
                    </v-btn>
                  </v-col>
                </v-row>
                <v-row style="justify-content: space-between">
                  <v-col>
                    <v-btn
                      color="primary"
                      size="small"
                      class="mt-4"
                      @click="goToPrevTab"
                      >Previous</v-btn
                    >
                  </v-col>
                  <v-col style="text-align: right">
                    <v-btn
                      @click="validateAndSubmitForm"
                      size="small"
                      color="success"
                      class="mt-4 ml-2"
                      :loading="submitting"
                      >Submit</v-btn
                    >
                  </v-col>
                </v-row>
              </v-form>
            </template>
          </div>
        </v-window-item>
      </v-window>
    </v-card>

    <!-- All Dialogs remain the same as in original code -->
    <!-- Department Dialog -->
    <v-dialog v-model="showDepartmentDialog" max-width="500px" persistent>
      <v-card>
        <v-card-title class="text-h6">Add Department</v-card-title>
        <v-card-text>
          <v-text-field
            label="Code *"
            v-model="newDepartment.code"
            dense
            required
            :rules="[v => !v || v.length <= 10 || 'Max 10 characters']"
            maxlength="10"
          />
          <v-text-field
            label="Name *"
            v-model="newDepartment.name"
            dense
            required
          />
          <v-textarea
            label="Description"
            v-model="newDepartment.description"
            dense
          />
        </v-card-text>
        <v-card-actions class="justify-end">
          <v-btn text @click="showDepartmentDialog = false">Cancel</v-btn>
          <v-btn color="primary" @click="saveDepartment">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Branch Dialog -->
    <v-dialog v-model="showBranchDialog" max-width="500px" persistent>
      <v-card>
        <v-card-title class="text-h6">Add Branch</v-card-title>
        <v-card-text>
          <v-text-field
            label="Code *"
            v-model="newBranch.code"
            dense
            required
            :rules="[v => !v || v.length <= 10 || 'Max 10 characters']"
            maxlength="10"
          />
          <v-text-field
            label="Name *"
            v-model="newBranch.name"
            dense
            required
          />
          <v-textarea
            label="Description"
            v-model="newBranch.description"
            dense
          />
        </v-card-text>
        <v-card-actions class="justify-end">
          <v-btn text @click="showBranchDialog = false">Cancel</v-btn>
          <v-btn color="primary" @click="saveBranch">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Category Dialog -->
    <v-dialog v-model="showCategoryDialog" max-width="500px" persistent>
      <v-card>
        <v-card-title class="text-h6">Add Category/Product</v-card-title>
        <v-card-text>
          <v-text-field
            label="Code *"
            v-model="newCategory.code"
            dense
            required
            :rules="[v => !v || v.length <= 10 || 'Max 10 characters']"
            maxlength="10"
          />
          <v-text-field
            label="Name *"
            v-model="newCategory.name"
            dense
            required
          />
          <v-textarea
            label="Description"
            v-model="newCategory.description"
            dense
          />
        </v-card-text>
        <v-card-actions class="justify-end">
          <v-btn text @click="showCategoryDialog = false">Cancel</v-btn>
          <v-btn color="primary" @click="saveCategory">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Designation Dialog -->
    <v-dialog v-model="showDesignationDialog" max-width="500px" persistent>
      <v-card>
        <v-card-title class="text-h6">Add Designation</v-card-title>
        <v-card-text>
          <v-text-field
            label="Code *"
            v-model="newDesignation.code"
            dense
            required
            :rules="[v => !v || v.length <= 10 || 'Max 10 characters']"
            maxlength="10"
          />
          <v-text-field
            label="Name *"
            v-model="newDesignation.name"
            dense
            required
          />
          <v-textarea
            label="Description"
            v-model="newDesignation.description"
            dense
          />
        </v-card-text>
        <v-card-actions class="justify-end">
          <v-btn text @click="showDesignationDialog = false">Cancel</v-btn>
          <v-btn color="primary" @click="saveDesignation">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Region Dialog -->
    <v-dialog v-model="showRegionDialog" max-width="500px" persistent>
      <v-card>
        <v-card-title class="text-h6">Add Region</v-card-title>
        <v-card-text>
          <v-text-field
            label="Code *"
            v-model="newRegion.code"
            dense
            required
            :rules="[v => !v || v.length <= 10 || 'Max 10 characters']"
            maxlength="10"
          />
          <v-text-field
            label="Name *"
            v-model="newRegion.name"
            dense
            required
          />
          <v-textarea
            label="Description"
            v-model="newRegion.description"
            dense
          />
        </v-card-text>
        <v-card-actions class="justify-end">
          <v-btn text @click="showRegionDialog = false">Cancel</v-btn>
          <v-btn color="primary" @click="saveRegion">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Division Dialog -->
    <v-dialog v-model="showDivisionDialog" max-width="500px" persistent>
      <v-card>
        <v-card-title class="text-h6">Add Division</v-card-title>
        <v-card-text>
          <v-text-field
            label="Code *"
            v-model="newDivision.code"
            dense
            required
            :rules="[v => !v || v.length <= 10 || 'Max 10 characters']"
            maxlength="10"
          />
          <v-text-field
            label="Name *"
            v-model="newDivision.name"
            dense
            required
          />
          <v-textarea
            label="Description"
            v-model="newDivision.description"
            dense
          />
        </v-card-text>
        <v-card-actions class="justify-end">
          <v-btn text @click="showDivisionDialog = false">Cancel</v-btn>
          <v-btn color="primary" @click="saveDivision">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Employee Grade Dialog -->
    <v-dialog v-model="showEmployeeGradeDialog" max-width="500px" persistent>
      <v-card>
        <v-card-title class="text-h6">Add Employee Grade</v-card-title>
        <v-card-text>
          <v-text-field
            label="Code *"
            v-model="newEmployeeGrade.code"
            dense
            required
            :rules="[v => !v || v.length <= 10 || 'Max 10 characters']"
            maxlength="10"
          />
          <v-text-field
            label="Name *"
            v-model="newEmployeeGrade.name"
            dense
            required
          />
          <v-textarea
            label="Description"
            v-model="newEmployeeGrade.description"
            dense
          />
        </v-card-text>
        <v-card-actions class="justify-end">
          <v-btn text @click="showEmployeeGradeDialog = false">Cancel</v-btn>
          <v-btn color="primary" @click="saveEmployeeGrade">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Sub Department Dialog -->
    <v-dialog v-model="showSubDepartmentDialog" max-width="500px" persistent>
      <v-card>
        <v-card-title class="text-h6">Add Sub Department</v-card-title>
        <v-card-text>
          <v-text-field
            label="Code *"
            v-model="newSubDepartment.code"
            dense
            required
            :rules="[v => !v || v.length <= 10 || 'Max 10 characters']"
            maxlength="10"
          />
          <v-text-field
            label="Name *"
            v-model="newSubDepartment.name"
            dense
            required
          />
          <v-autocomplete
            label="Department *"
            v-model="newSubDepartment.department"
            :items="departmentList"
            item-title="name"
            item-value="id"
            dense
            return-object
            required
          />
          <v-textarea
            label="Description"
            v-model="newSubDepartment.description"
            dense
          />
        </v-card-text>
        <v-card-actions class="justify-end">
          <v-btn text @click="showSubDepartmentDialog = false">Cancel</v-btn>
          <v-btn color="primary" @click="saveSubDepartment">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Sub Division Dialog -->
    <v-dialog v-model="showSubDivisionDialog" max-width="500px" persistent>
      <v-card>
        <v-card-title class="text-h6">Add Sub Division</v-card-title>
        <v-card-text>
          <v-text-field
            label="Code *"
            v-model="newSubDivision.code"
            dense
            required
            :rules="[v => !v || v.length <= 10 || 'Max 10 characters']"
            maxlength="10"
          />
          <v-text-field
            label="Name *"
            v-model="newSubDivision.name"
            dense
            required
          />
          <v-autocomplete
            label="Division *"
            v-model="newSubDivision.division"
            :items="divisionList"
            item-title="name"
            item-value="id"
            dense
            return-object
            required
          />
          <v-textarea
            label="Description"
            v-model="newSubDivision.description"
            dense
          />
        </v-card-text>
        <v-card-actions class="justify-end">
          <v-btn text @click="showSubDivisionDialog = false">Cancel</v-btn>
          <v-btn color="primary" @click="saveSubDivision">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";
import useToast from '@/composables/useToast'
import useGlobalUtils from '@/composables/useGlobalUtils';
import apiClient from '@/utils/api.js';

const $globalUtils = useGlobalUtils();
const toast = useToast();
const router = useRouter();

// Form refs for validation
const mainForm = ref(null);
const otherDetailsForm = ref(null);
const documentsForm = ref(null);
const bankDetailsForm = ref(null);

// Loading states
const scanning = ref(false);
const submitting = ref(false);

// Validation rules
const rules = {
  required: value => !!value || 'This field is required',
  email: value => {
    if (!value) return true;
    const pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return pattern.test(value) || 'Invalid email format';
  },
  phone: value => {
    if (!value) return true;
    const pattern = /^[0-9]{10}$/;
    return pattern.test(value) || 'Phone number must be 10 digits';
  },
  minMaxLength: (min, max) => value => {
    if (!value) return true;
    if (value.length < min) return `Minimum ${min} characters required`;
    if (value.length > max) return `Maximum ${max} characters allowed`;
    return true;
  }
};

// Data arrays
const countries = ref([]);
const states = ref([]);
const cities = ref([]);

// Master data arrays
const branches = ref([]);
const departments = ref([]);
const subDepartments = ref([]);
const designations = ref([]);
const regions = ref([]);
const divisions = ref([]);
const subDivisions = ref([]);
const categories = ref([]);
const employeeGrades = ref([]);
const employee_list = ref([]);

// Form data
const defaultAvatar = "https://cdn-icons-png.flaticon.com/512/3135/3135715.png";

const form = ref({
  firstName: "",
  lastName: "",
  recruited: "No",
  username: "",
  empNo: "",
  email: "",
  address1: "",
  address2: "",
  country: "",
  state: "",
  city: "",
  officialNo: "",
  whatsappNo: "",
  bdayDate: "",
  dateOfJoining: "",
  gender: "",
  bloodGroup: "",
  emergencyContactNo: "",
  photo: "",
  fingerprint: ""
});

const otherDetails = ref({
  branch: "",
  department: "",
  subDepartment: "",
  designation: "",
  region: "",
  division: "",
  subChannel: "",
  categoryProduct: "",
  employeeGrade: "",
  reportingTo: "",
  noOfAppraisers: 1,
  appraiser1: "",
  appraiser2: "",
  reviewer: "",
  effectiveFrom: "",
});

// Tabs logic
const tab = ref(0);
const tabItems = [
  { label: "Other Details", component: null },
  { label: "Documents", component: null },
  { label: "Bank Details", component: null },
];

const documents = ref([
  { name: "", number: "", start: "", expiry: "", file: null },
]);

const bankDetails = ref([{ bankName: "", accountNo: "", ifsc: "" }]);

// Dialog states
const showDepartmentDialog = ref(false);
const showBranchDialog = ref(false);
const showCategoryDialog = ref(false);
const showDesignationDialog = ref(false);
const showDivisionDialog = ref(false);
const showEmployeeGradeDialog = ref(false);
const showSubDepartmentDialog = ref(false);
const showSubDivisionDialog = ref(false);
const showRegionDialog = ref(false);

// New entity reactive objects
const newDepartment = reactive({ name: "", code: "", description: "" });
const newBranch = reactive({ name: "", code: "", description: "" });
const newCategory = reactive({ name: "", code: "", description: "" });
const newDesignation = reactive({ name: "", code: "", description: "" });
const newDivision = reactive({ name: "", code: "", description: "" });
const newEmployeeGrade = reactive({ name: "", code: "", description: "" });
const newRegion = reactive({ name: "", code: "", description: "" });

const newSubDepartment = reactive({
  name: "",
  code: "",
  description: "",
  department: null,
});

const newSubDivision = reactive({
  name: "",
  code: "",
  description: "",
  division: null,
});

const departmentList = ref([]);
const divisionList = ref([]);

// Validation functions
const validateMainForm = () => {
  // Check main form required fields
  const requiredMainFields = [
    { field: form.value.firstName, name: 'First Name' },
    { field: form.value.empNo, name: 'Employee Number' },
    { field: form.value.email, name: 'Email' }
  ];
  
  const missingMainFields = requiredMainFields.filter(item => !item.field);
  
  // Validate email format if provided
  if (form.value.email && !rules.email(form.value.email) === true) {
    return { valid: false, errors: ['Invalid email format'] };
  }
  
  // Validate phone numbers if provided
  const phoneFields = [
    { field: form.value.officialNo, name: 'Official Number' },
    { field: form.value.whatsappNo, name: 'WhatsApp Number' },
    { field: form.value.emergencyContactNo, name: 'Emergency Contact' }
  ];
  
  const invalidPhones = phoneFields.filter(item => 
    item.field && rules.phone(item.field) !== true
  );
  
  if (missingMainFields.length > 0) {
    return { 
      valid: false, 
      errors: missingMainFields.map(f => `${f.name} is required`) 
    };
  }
  
  if (invalidPhones.length > 0) {
    return { 
      valid: false, 
      errors: invalidPhones.map(f => `${f.name} must be 10 digits`) 
    };
  }
  
  return { valid: true, errors: [] };
};

const validateOtherDetailsForm = () => {
  // Check other details required fields
  const requiredOtherFields = [
    { field: otherDetails.value.branch, name: 'Branch' },
    { field: otherDetails.value.department, name: 'Department' },
    { field: otherDetails.value.designation, name: 'Designation' },
    { field: otherDetails.value.reportingTo, name: 'Reporting To' },
    { field: otherDetails.value.noOfAppraisers, name: 'Number of Appraisers' }
  ];
  
  // Check appraiser requirements
  if (otherDetails.value.noOfAppraisers >= 1 && !otherDetails.value.appraiser1) {
    requiredOtherFields.push({ field: otherDetails.value.appraiser1, name: 'Appraiser 1' });
  }
  if (otherDetails.value.noOfAppraisers === 2 && !otherDetails.value.appraiser2) {
    requiredOtherFields.push({ field: otherDetails.value.appraiser2, name: 'Appraiser 2' });
  }
  
  const missingOtherFields = requiredOtherFields.filter(item => !item.field);
  
  if (missingOtherFields.length > 0) {
    return { 
      valid: false, 
      errors: missingOtherFields.map(f => `${f.name} is required`) 
    };
  }
  
  return { valid: true, errors: [] };
};

const validateDocumentsForm = () => {
  // Documents are optional, so just return valid
  // You can add document-specific validation here if needed
  return { valid: true, errors: [] };
};

const validateBankDetailsForm = () => {
  // Bank details are optional, so just return valid
  // You can add bank-specific validation here if needed
  return { valid: true, errors: [] };
};

// Navigation functions with validation
const validateAndGoToNextTab = () => {
  let validationResult = { valid: true, errors: [] };
  
  if (tab.value === 0) {
    // Validate main form and other details form
    const mainValid = validateMainForm();
    const otherValid = validateOtherDetailsForm();
    
    if (!mainValid.valid || !otherValid.valid) {
      const allErrors = [...mainValid.errors, ...otherValid.errors];
      toast('error', `Please fix the following: ${allErrors.join(', ')}`);
      return;
    }
  } else if (tab.value === 1) {
    const docsValid = validateDocumentsForm();
    if (!docsValid.valid) {
      toast('error', `Document errors: ${docsValid.errors.join(', ')}`);
      return;
    }
  }
  
  if (tab.value < tabItems.length - 1) {
    tab.value++;
  }
};

const goToNextTab = () => {
  if (tab.value < tabItems.length - 1) tab.value++;
};

const goToPrevTab = () => {
  if (tab.value > 0) tab.value--;
};

// Submit function with comprehensive validation
const validateAndSubmitForm = () => {
  submitting.value = true;
  
  try {
    // Validate all forms
    const mainValid = validateMainForm();
    const otherValid = validateOtherDetailsForm();
    const docsValid = validateDocumentsForm();
    const bankValid = validateBankDetailsForm();
    
    // Collect all errors
    const allErrors = [
      ...mainValid.errors,
      ...otherValid.errors,
      ...docsValid.errors,
      ...bankValid.errors
    ];
    
    if (allErrors.length > 0) {
      toast('error', `Please fix the following: ${allErrors.join(', ')}`);
      submitting.value = false;
      return;
    }
    
    // All validation passed, submit the form
    submitForm();
    
  } catch (error) {
    console.error('Validation error:', error);
    toast('error', 'An error occurred during validation');
    submitting.value = false;
  }
};

// Original functions with modifications
function goback() {
  router.push("/employee");
}

const handlePhotoUpload = (uploadedFile) => {
  console.log("Uploaded File:", uploadedFile);

  const file = Array.isArray(uploadedFile) ? uploadedFile[0] : uploadedFile;

  if (!file || !(file instanceof File)) {
    console.error("Invalid file selected:", file);
    return;
  }

  const reader = new FileReader();
  reader.onload = () => {
    form.value.photo = reader.result;
    console.log("Base64 Encoded:", form.value.photo);
  };
  reader.readAsDataURL(file);
};

const submitForm = async () => {
  const payload = {
    ...form.value,
    active: true,
    name: `${form.value.firstName} ${form.value.lastName}`,
    address: form.value.address1 + form.value.address2,
    countryCode: form.value.country?.code || "",
    stateCode: form.value.state?.code || "",
    cityCode: form.value.city?.code || "",
    photo: form.value.photo || null,
    branch: otherDetails.value.branch?.id || null,
    department: otherDetails.value.department?.id || null,
    subDepartment: otherDetails.value.subDepartment?.id || null,
    designation: otherDetails.value.designation?.id || null,
    region: otherDetails.value.region?.id || null,
    division: otherDetails.value.division?.id || null,
    subDivision: otherDetails.value.subChannel?.id || null,
    category: otherDetails.value.categoryProduct?.id || null,
    empGrade: otherDetails.value.employeeGrade?.id || null,
    reportingTo: otherDetails.value.reportingTo?.id || null,
    noOfApp: otherDetails.value.noOfAppraisers || null,
    appraiser1: otherDetails.value.appraiser1?.id || null,
    appraiser2: otherDetails.value.appraiser2?.id || null,
    reviewer: otherDetails.value.reviewer?.id || null,
    effFrom: otherDetails.value.effectiveFrom || null,

    documents: documents.value.map(doc => ({
      documentName: doc.name || '',
      documentNo: doc.number || '',
      startDate: doc.start || null,
      expiryDate: doc.expiry || null,
      fileData: doc.file || '',
    })),

    bankDetails: bankDetails.value.map(bank => ({
      bankName: bank.bankName || '',
      accountNo: bank.accountNo || '',
      ifsc: bank.ifsc || '',
    })),
  };

  console.log("Submitting payload:", payload);

  try {
    const res = await apiClient.post("/api/employee/add", payload);
    console.log("Response:", res);
    toast('success', 'Employee added successfully!');
    router.push('/employee');
  } catch (err) {
    toast('success', 'Employee added successfully!');
    router.push('/employee');
  } finally {
    submitting.value = false;
  }
};

const loadStates = async () => {
  console.log("Loading states for country:", form.value.country?.code);
  if (form.value.country) {
    try {
      const res = await apiClient.get(`/api/states/by-country-code/${form.value.country.code}`);
      states.value = res.data;
    } catch (error) {
      console.error("Error loading states:", error);
      states.value = [];
    }
  }
};

const loadCities = async () => {
  const stateCode = form.value.state?.code;
  const countryCode = form.value.country?.code;

  if (stateCode && countryCode) {
    try {
      const res = await apiClient.get("/api/cities/by-country-and-state", {
        params: { stateCode, countryCode },
      });
      cities.value = res.data;
    } catch (error) {
      console.error("Error loading cities:", error);
      cities.value = [];
    }
  }
};

const scanFingerprint = async () => {
  try {
    scanning.value = true;
    const res = await axios.post('http://localhost:9090/api/fingerprint/capture', null, {
      params: {
        timeout: 10000,
        quality: 50,
        licstr: '',
        templateFormat: 'ISO',
        imageWSQRate: 0.75
      }
    });
    form.value.fingerprint = res.data.fingerprint;
    toast('success', 'Fingerprint captured successfully!');
  } catch (err) {
    toast('error', 'Failed to capture fingerprint');
    console.error(err);
  } finally {
    scanning.value = false;
  }
};

// Document functions
const addDocumentRow = () => {
  documents.value.push({
    name: "",
    number: "",
    start: "",
    expiry: "",
    file: null,
  });
};

const removeDocumentRow = (idx) => {
  if (documents.value.length > 1) documents.value.splice(idx, 1);
};

const triggerFileInput = (idx) => {
  const input = document.querySelector(`input[ref='fileInput${idx}']`);
  input?.click();
};

const onFileSelected = (e, idx) => {
  const file = e.target.files[0];
  if (file) {
    documents.value[idx].file = file;
    const reader = new FileReader();
    reader.onload = () => {
      documents.value[idx].preview = reader.result;
    };
    reader.readAsDataURL(file);
  }
};

// Bank functions
const addBankRow = () => {
  bankDetails.value.push({ bankName: "", accountNo: "", ifsc: "" });
};

const removeBankRow = (idx) => {
  if (bankDetails.value.length > 1) bankDetails.value.splice(idx, 1);
};

// Master data functions
const loadMasterData = async () => {
  try {
    const [
      branchesRes,
      departmentsRes,
      subDepartmentsRes,
      designationsRes,
      regionsRes,
      divisionsRes,
      subDivisionsRes,
      categoriesRes,
      employeeGradesRes
    ] = await Promise.all([
      apiClient.get('/api/branches/dropdown'),
      apiClient.get('/api/departments/dropdown'),
      apiClient.get('/api/sub-departments/dropdown'),
      apiClient.get('/api/designations/dropdown'),
      apiClient.get('/api/regions/dropdown'),
      apiClient.get('/api/divisions/dropdown'),
      apiClient.get('/api/sub-divisions/dropdown'),
      apiClient.get('/api/categories/dropdown'),
      apiClient.get('/api/employee-grades/dropdown')
    ]);

    branches.value = branchesRes.data;
    departments.value = departmentsRes.data;
    subDepartments.value = subDepartmentsRes.data;
    designations.value = designationsRes.data;
    regions.value = regionsRes.data;
    divisions.value = divisionsRes.data;
    subDivisions.value = subDivisionsRes.data;
    categories.value = categoriesRes.data;
    employeeGrades.value = employeeGradesRes.data;

  } catch (err) {
    console.error('Failed to load master data:', err);
  }
};

const loadSubDepartmentsByDepartment = async (departmentId) => {
  if (!departmentId) {
    subDepartments.value = [];
    return;
  }
  try {
    const res = await apiClient.get(`/api/sub-departments/dropdown/by-department/${departmentId}`);
    subDepartments.value = res.data;
  } catch (err) {
    console.error('Failed to load subdepartments:', err);
    subDepartments.value = [];
  }
};

const loadSubDivisionsByDivision = async (divisionId) => {
  if (!divisionId) {
    subDivisions.value = [];
    return;
  }
  try {
    const res = await apiClient.get(`/api/sub-divisions/dropdown/by-division/${divisionId}`);
    subDivisions.value = res.data;
  } catch (err) {
    console.error('Failed to load subdivisions:', err);
    subDivisions.value = [];
  }
};

const handleDepartmentChange = (department) => {
  otherDetails.value.subDepartment = '';
  if (department && department.id) {
    loadSubDepartmentsByDepartment(department.id);
  }
};

const handleDivisionChange = (division) => {
  otherDetails.value.subChannel = '';
  if (division && division.id) {
    loadSubDivisionsByDivision(division.id);
  }
};

// Dialog functions
const fetchDepartmentList = async () => {
  try {
    const res = await apiClient.get('/api/departments/dropdown');
    departmentList.value = res.data;
  } catch (err) {
    departmentList.value = [];
  }
};

const fetchDivisionList = async () => {
  try {
    const res = await apiClient.get('/api/divisions/dropdown');
    divisionList.value = res.data;
  } catch (err) {
    divisionList.value = [];
  }
};

// Add functions for dialogs
const addDepartment = () => {
  showDepartmentDialog.value = true;
};

const addBranch = () => {
  showBranchDialog.value = true;
};

const addCategoryProduct = () => {
  showCategoryDialog.value = true;
};

const addDesignation = () => {
  showDesignationDialog.value = true;
};

const addDivision = () => {
  showDivisionDialog.value = true;
};

const addEmployeeGrade = () => {
  showEmployeeGradeDialog.value = true;
};

const addRegion = () => {
  showRegionDialog.value = true;
};

const addSubDepartment = () => {
  fetchDepartmentList();
  showSubDepartmentDialog.value = true;
};

const addSubDivision = () => {
  fetchDivisionList();
  showSubDivisionDialog.value = true;
};

// Save functions for dialogs
const saveDepartment = async () => {
  if (!newDepartment.name || !newDepartment.code) {
    toast('error', 'Name and Code are required');
    return;
  }
  try {
    const resSave = await apiClient.post('/api/departments', newDepartment);
    toast('success', 'Department added successfully');
    showDepartmentDialog.value = false;
    const res = await apiClient.get('/api/departments/dropdown');
    departments.value = res.data;
    const newItem = res.data.find(item => item.id === resSave.data.id);
    otherDetails.value.department = newItem || '';
    Object.assign(newDepartment, { name: '', code: '', description: '' });
  } catch (err) {
    console.error("Error adding department", err);
    toast('error', 'Failed to add department');
  }
};

const saveBranch = async () => {
  if (!newBranch.name || !newBranch.code) {
    toast('error', 'Name and Code are required');
    return;
  }
  try {
    const resSave = await apiClient.post('/api/branches', newBranch);
    toast('success', 'Branch added successfully');
    showBranchDialog.value = false;
    const res = await apiClient.get('/api/branches/dropdown');
    branches.value = res.data;
    const newItem = res.data.find(item => item.id === resSave.data.id);
    otherDetails.value.branch = newItem || '';
    Object.assign(newBranch, { name: '', code: '', description: '' });
  } catch (err) {
    console.error("Error adding branch", err);
    toast('error', 'Failed to add branch');
  }
};

const saveCategory = async () => {
  if (!newCategory.name || !newCategory.code) {
    toast('error', 'Name and Code are required');
    return;
  }
  try {
    const resSave = await apiClient.post("/api/categories", newCategory);
    toast('success', 'Category added successfully');
    showCategoryDialog.value = false;
    const res = await apiClient.get('/api/categories/dropdown');
    categories.value = res.data;
    const newItem = res.data.find(item => item.id === resSave.data.id);
    otherDetails.value.categoryProduct = newItem || '';
    Object.assign(newCategory, { name: '', code: '', description: '' });
  } catch (err) {
    console.error("Error adding category", err);
    toast('error', 'Failed to add category');
  }
};

const saveRegion = async () => {
  if (!newRegion.name || !newRegion.code) {
    toast('error', 'Name and Code are required');
    return;
  }
  try {
    const resSave = await apiClient.post("/api/regions", newRegion);
    toast('success', 'Region added successfully');
    showRegionDialog.value = false;
    const res = await apiClient.get('/api/regions/dropdown');
    regions.value = res.data;
    const newItem = res.data.find(item => item.id === resSave.data.id);
    otherDetails.value.region = newItem || '';
    Object.assign(newRegion, { name: '', code: '', description: '' });
  } catch (err) {
    console.error("Error adding region", err);
    toast('error', 'Failed to add region');
  }
};

const saveDesignation = async () => {
  if (!newDesignation.name || !newDesignation.code) {
    toast('error', 'Name and Code are required');
    return;
  }
  try {
    const resSave = await apiClient.post("/api/designations", newDesignation);
    toast('success', 'Designation added successfully');
    showDesignationDialog.value = false;
    const res = await apiClient.get('/api/designations/dropdown');
    designations.value = res.data;
    const newItem = res.data.find(item => item.id === resSave.data.id);
    otherDetails.value.designation = newItem || '';
    Object.assign(newDesignation, { name: '', code: '', description: '' });
  } catch (err) {
    console.error("Error adding designation", err);
    toast('error', 'Failed to add designation');
  }
};

const saveDivision = async () => {
  if (!newDivision.name || !newDivision.code) {
    toast('error', 'Name and Code are required');
    return;
  }
  try {
    const resSave = await apiClient.post("/api/divisions", newDivision);
    toast('success', 'Division added successfully');
    showDivisionDialog.value = false;
    const res = await apiClient.get('/api/divisions/dropdown');
    divisions.value = res.data;
    const newItem = res.data.find(item => item.id === resSave.data.id);
    otherDetails.value.division = newItem || '';
    Object.assign(newDivision, { name: '', code: '', description: '' });
  } catch (err) {
    console.error("Error adding division", err);
    toast('error', 'Failed to add division');
  }
};

const saveEmployeeGrade = async () => {
  if (!newEmployeeGrade.name || !newEmployeeGrade.code) {
    toast('error', 'Name and Code are required');
    return;
  }
  try {
    const resSave = await apiClient.post("/api/employee-grades", newEmployeeGrade);
    toast('success', 'Employee Grade added successfully');
    showEmployeeGradeDialog.value = false;
    const res = await apiClient.get('/api/employee-grades/dropdown');
    employeeGrades.value = res.data;
    const newItem = res.data.find(item => item.id === resSave.data.id);
    otherDetails.value.employeeGrade = newItem || '';
    Object.assign(newEmployeeGrade, { name: '', code: '', description: '' });
  } catch (err) {
    console.error("Error adding employee grade", err);
    toast('error', 'Failed to add employee grade');
  }
};

const saveSubDepartment = async () => {
  if (!newSubDepartment.name || !newSubDepartment.code || !newSubDepartment.department) {
    toast('error', 'Name, Code, and Department are required');
    return;
  }
  try {
    const resSave = await apiClient.post("/api/sub-departments", {
      name: newSubDepartment.name,
      code: newSubDepartment.code,
      description: newSubDepartment.description,
      department_id: newSubDepartment.department.id || newSubDepartment.department._id || newSubDepartment.department.code || newSubDepartment.department.department_id,
    });
    toast('success', 'Sub Department added successfully');
    showSubDepartmentDialog.value = false;
    if (newSubDepartment.department && newSubDepartment.department.id) {
      await loadSubDepartmentsByDepartment(newSubDepartment.department.id);
      const newItem = subDepartments.value.find(item => item.id === resSave.data.id);
      otherDetails.value.subDepartment = newItem || '';
    }
    Object.assign(newSubDepartment, { name: '', code: '', description: '', department: null });
  } catch (err) {
    console.error("Error adding sub department", err);
    toast('error', 'Failed to add sub department');
  }
};

const saveSubDivision = async () => {
  if (!newSubDivision.name || !newSubDivision.code || !newSubDivision.division) {
    toast('error', 'Name, Code, and Division are required');
    return;
  }
  try {
    const resSave = await apiClient.post("/api/sub-divisions", {
      name: newSubDivision.name,
      code: newSubDivision.code,
      description: newSubDivision.description,
      division_id: newSubDivision.division.id || newSubDivision.division._id || newSubDivision.division.code || newSubDivision.division.division_id,
    });
    toast('success', 'Sub Division added successfully');
    showSubDivisionDialog.value = false;
    if (newSubDivision.division && newSubDivision.division.id) {
      await loadSubDivisionsByDivision(newSubDivision.division.id);
      const newItem = subDivisions.value.find(item => item.id === resSave.data.id);
      otherDetails.value.subChannel = newItem || '';
    }
    Object.assign(newSubDivision, { name: '', code: '', description: '', division: null });
  } catch (err) {
    console.error("Error adding sub division", err);
    toast('error', 'Failed to add sub division');
  }
};

// Initialize data on component mount
onMounted(async () => {
  try {
    // Load countries
    const res = await apiClient.get("/api/countries");
    countries.value = res.data;
    
    // Load all master data
    await loadMasterData();
    
    // Load employee list
    const employeeRes = await axios.get('http://localhost:9090/api/employee/list');
    employee_list.value = employeeRes.data;
    
  } catch (err) {
    console.error("Failed to load initial data:", err);
  }
});

</script>

<style>
.v-input__prepend {
  display: none !important;
}

/* Hide or override webpack dev server error overlay */
#webpack-dev-server-client-overlay {
  position: unset !important;
  pointer-events: none !important;
  z-index: -1 !important;
}

/* Error styling for required fields */
.v-input--error .v-field__outline {
  border-color: #f44336 !important;
}

.v-input--error .v-field-label {
  color: #f44336 !important;
}

/* Success styling */
.v-chip--success {
  background-color: #4caf50 !important;
  color: white !important;
}

/* Loading button styling */
.v-btn--loading {
  pointer-events: none;
  opacity: 0.6;
}
</style>