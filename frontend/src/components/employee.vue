<template>
  <div class="ma-6">
    <v-card elevation="5" class="px-6">
      <v-row class="d-flex justify-space-between align-center mb-2 pt-5">
        <h2>Add Employee Details</h2>
        <v-btn color="primary" size="small" @click="goback">Back</v-btn>
      </v-row>

      <v-form @submit.prevent="submitForm">
        <v-row dense wrap>
          <!-- Name Fields -->
          <v-col cols="12" sm="6" md="3">
            <v-text-field
              label="Employee First Name *"
              v-model="form.firstName"
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
            />
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-text-field label="Employee No *" v-model="form.empNo" required />
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-text-field label="Email *" v-model="form.email" required />
          </v-col>

          <!-- Address -->
          <v-col cols="12" sm="6" md="3">
            <v-text-field label="Address Line 1" v-model="form.address1" />
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-text-field label="Address Line 2" v-model="form.address2" />
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <!-- <v-autocomplete label="Country" v-model="form.country" /> -->
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
            <v-text-field label="Official No 1" v-model="form.officialNo" />
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-text-field label="WhatsApp No" v-model="form.whatsappNo" />
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
              v-model="form.emergency_contact_no"
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
              @change="handlePhotoUpload"
            />
          </v-col>
        </v-row>

        <!-- Submit -->
        <!-- <v-btn type="submit" color="primary" class="mt-4">Submit</v-btn> -->
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
              <v-form>
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
                        v-model="otherDetails.channelDivision"
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
                        :disabled="!otherDetails.channelDivision"
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

                  <!-- Rest of your fields remain the same -->
                  <v-col cols="12" sm="6" md="3">
                    <v-autocomplete
                      label="Reporting To *"
                      :items="[]"
                      v-model="otherDetails.reportingTo"
                      required
                    />
                  </v-col>
                  <v-col cols="12" sm="6" md="3">
                    <v-select
                      label="No of Appraisers *"
                      :items="[1, 2]"
                      v-model="otherDetails.noOfAppraisers"
                      required
                    />
                  </v-col>
                  <v-col cols="12" sm="6" md="3" v-if="otherDetails.noOfAppraisers >= 1">
                    <v-autocomplete
                      label="Appraiser 1"
                      :items="[]"
                      v-model="otherDetails.appraiser1"
                    />
                  </v-col>
                  <v-col cols="12" sm="6" md="3" v-if="otherDetails.noOfAppraisers == 2">
                    <v-autocomplete
                      label="Appraiser 2"
                      :items="[]"
                      v-model="otherDetails.appraiser2"
                    />
                  </v-col>
                  <v-col cols="12" sm="6" md="3">
                    <v-autocomplete label="Reviewer" :items="[]" v-model="otherDetails.reviewer" />
                  </v-col>
                  <v-col cols="12" sm="6" md="3">
                    <v-text-field label="Effective From" v-model="otherDetails.effectiveFrom" type="date" />
                  </v-col>
                </v-row>
                <v-row>
                    <v-col cols="12" style="text-align: right;">
                        <v-btn color="primary" size="small" class="mt-4" @click="goToNextTab">Next</v-btn>
                    </v-col>
                </v-row>
              </v-form>
            </template>

            <!-- Documents Tab -->
            <template v-else-if="tab === 1">
              <v-form>
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
                    <v-btn icon @click="onDocumentUpload(idx)">
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
              <v-form @submit.prevent="submitForm">
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
                      @click="submitForm"
                      size="small"
                      color="success"
                      class="mt-4 ml-2"
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

    <!-- Division Dialog -->
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



const $globalUtils = useGlobalUtils(); // like global Functions use globalUtils.anyfuction()
const toast = useToast() // like global Functions use toast('success', 'message') or toast('error', 'message')
const router = useRouter();
const countries = ref([]);
const states = ref([]);
const cities = ref([]);
// const loading = ref(false)
onMounted(async () => {
  try {
    const res = await axios.get("http://localhost:9090/api/countries");
    countries.value = res.data;
  } catch (err) {
    console.error("Failed to load countries:", err);
  } finally {
  }
});

function goback() {
  router.push("/employee");
}

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
});

const otherDetails = ref({
  branch: "",
  department: "",
  subDepartment: "",
  designation: "",
  region: "",
  channelDivision: "",
  subChannel: "",
  categoryProduct: "",
  employeeGrade: "",
  reportingTo: "",
  reportingToError: false,
  reportingToErrorMsg: "Reporting To is required",
  noOfAppraisers: 1,
  appraiser1: "",
  appraiser2: "", // add this line
  reviewer: "",
  toBeReplacedFor: "",
  effectiveFrom: "",
});

const handlePhotoUpload = (e) => {
  const file = e.target.files[0];
  const reader = new FileReader();
  reader.onloadend = () => {
    form.value.photo = reader.result;
  };
  if (file) reader.readAsDataURL(file);
};


const submitForm = async () => {
  const payload = {
    ...form.value,
    name: `${form.value.firstName} ${form.value.lastName}`,
    address: form.address1 + form.address2,
    countryCode: form.value.country?.code || "",
    stateCode: form.value.state?.code || "",
    cityCode: form.value.city?.code || "",
  };

  console.log("Submitting payload:", payload);

  try {
    const res = await axios.post(
      "http://localhost:9090/api/employee/add",
      payload
    );
    console.log("Response:", res);
    toast('success', 'Employee added successfully!');
  } catch (err) {
    console.error("Submission error:", err);
    toast('error', 'Failed to add employee');
  }
};

const loadStates = async () => {
  // console.log('Loading states for country:', country)
  console.log("Loading", form._rawValue.country.code);
  if (form._rawValue.country) {
    const res = await axios.get(
      `http://localhost:9090/api/states/by-country-code/${form._rawValue.country.code}`
    );
    states.value = res.data;
  }
};
const loadCities = async () => {
  const stateCode = form._rawValue.state?.code;
  const countryCode = form._rawValue.country?.code;

  if (stateCode && countryCode) {
    try {
      const res = await axios.get(
        "http://localhost:9090/api/cities/by-country-and-state",
        {
          params: {
            stateCode,
            countryCode,
          },
        }
      );
      cities.value = res.data; // or whatever you're using to store city data
    } catch (error) {
      console.error("Error loading cities:", error);
      cities.value = [];
    }
  }
};

// Tabs logic
const tab = ref(0);
const tabItems = [
  { label: "Other Details", component: null },
  { label: "Documents", component: null },
  { label: "Bank Details", component: null },
];

const goToNextTab = () => {
  if (tab.value < tabItems.length - 1) tab.value++;
};
const documents = ref([
  { name: "", number: "", start: "", expiry: "", file: null },
]);
const bankDetails = ref([{ bankName: "", accountNo: "", ifsc: "" }]);

// Documents
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
const onDocumentUpload = (idx) => {
  /* handle upload logic if needed */
};

// Bank Details
const addBankRow = () => {
  bankDetails.value.push({ bankName: "", accountNo: "", ifsc: "" });
};
const removeBankRow = (idx) => {
  if (bankDetails.value.length > 1) bankDetails.value.splice(idx, 1);
};

// Tab navigation
const goToPrevTab = () => {
  if (tab.value > 0) tab.value--;
};

// Dialogs and save logic for all masters
const showDepartmentDialog = ref(false);
const showBranchDialog = ref(false);
const showCategoryDialog = ref(false);
const showDesignationDialog = ref(false);
const showDivisionDialog = ref(false);
const showEmployeeGradeDialog = ref(false);
const showSubDepartmentDialog = ref(false);
const showSubDivisionDialog = ref(false);
const showRegionDialog = ref(false);

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

// Fetch department and division lists for dialogs
const fetchDepartmentList = async () => {
  try {
    const res = await axios.get('http://localhost:9090/api/departments/dropdown');
    departmentList.value = res.data;
  } catch (err) {
    departmentList.value = [];
  }
};

const fetchDivisionList = async () => {
  try {
    const res = await axios.get('http://localhost:9090/api/divisions/dropdown');
    divisionList.value = res.data;
  } catch (err) {
    divisionList.value = [];
  }
};

// Open dialog and fetch list
const addSubDepartment = () => {
  fetchDepartmentList();
  showSubDepartmentDialog.value = true;
};
const addSubDivision = () => {
  fetchDivisionList();
  showSubDivisionDialog.value = true;
};

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
// (Handled above with fetchDepartmentList/fetchDivisionList)

const saveDepartment = async () => {
  if (!newDepartment.name || !newDepartment.code) {
    toast('error', 'Name and Code are required');
    return;
  }
  try {
    const resSave = await axios.post('http://localhost:9090/api/departments', newDepartment);
    toast('success', 'Department added successfully');
    showDepartmentDialog.value = false;
    // Reload departments and set new value
    const res = await axios.get('http://localhost:9090/api/departments/dropdown');
    departments.value = res.data;
    // Set the new department as selected (find by id)
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
    const resSave = await axios.post('http://localhost:9090/api/branches', newBranch);
    toast('success', 'Branch added successfully');
    showBranchDialog.value = false;
    // Reload branches and set new value
    const res = await axios.get('http://localhost:9090/api/branches/dropdown');
    branches.value = res.data;
    // Set the new branch as selected (find by id)
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
    const resSave = await axios.post("http://localhost:9090/api/categories", newCategory);
    toast('success', 'Category added successfully');
    showCategoryDialog.value = false;
    // Reload categories and set new value
    const res = await axios.get('http://localhost:9090/api/categories/dropdown');
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
    const resSave = await axios.post("http://localhost:9090/api/regions", newRegion);
    toast('success', 'Region added successfully');
    showRegionDialog.value = false;
    // Reload regions and set new value
    const res = await axios.get('http://localhost:9090/api/regions/dropdown');
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
    const resSave = await axios.post("http://localhost:9090/api/designations", newDesignation);
    toast('success', 'Designation added successfully');
    showDesignationDialog.value = false;
    // Reload designations and set new value
    const res = await axios.get('http://localhost:9090/api/designations/dropdown');
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
    const resSave = await axios.post("http://localhost:9090/api/divisions", newDivision);
    toast('success', 'Division added successfully');
    showDivisionDialog.value = false;
    // Reload divisions and set new value
    const res = await axios.get('http://localhost:9090/api/divisions/dropdown');
    divisions.value = res.data;
    const newItem = res.data.find(item => item.id === resSave.data.id);
    otherDetails.value.channelDivision = newItem || '';
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
    const resSave = await axios.post(
      "http://localhost:9090/api/employee-grades",
      newEmployeeGrade
    );
    toast('success', 'Employee Grade added successfully');
    showEmployeeGradeDialog.value = false;
    // Reload employee grades and set new value
    const res = await axios.get('http://localhost:9090/api/employee-grades/dropdown');
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
  if (
    !newSubDepartment.name ||
    !newSubDepartment.code ||
    !newSubDepartment.department
  ) {
    toast('error', 'Name, Code, and Department are required');
    return;
  }
  try {
    const resSave = await axios.post("http://localhost:9090/api/sub-departments", {
      name: newSubDepartment.name,
      code: newSubDepartment.code,
      description: newSubDepartment.description,
      department_id:
        newSubDepartment.department.id ||
        newSubDepartment.department._id ||
        newSubDepartment.department.code ||
        newSubDepartment.department.department_id,
    });
    toast('success', 'Sub Department added successfully');
    showSubDepartmentDialog.value = false;
    // Reload subDepartments for the selected department and select the new one
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
  if (
    !newSubDivision.name ||
    !newSubDivision.code ||
    !newSubDivision.division
  ) {
    toast('error', 'Name, Code, and Division are required');
    return;
  }
  try {
    const resSave = await axios.post("http://localhost:9090/api/sub-divisions", {
      name: newSubDivision.name,
      code: newSubDivision.code,
      description: newSubDivision.description,
      division_id:
        newSubDivision.division.id ||
        newSubDivision.division._id ||
        newSubDivision.division.code ||
        newSubDivision.division.division_id,
    });
    toast('success', 'Sub Division added successfully');
    showSubDivisionDialog.value = false;
    // Reload subDivisions for the selected division and select the new one
    if (newSubDivision.division && newSubDivision.division.id) {
      await loadSubDivisionsByDivision(newSubDivision.division.id);
      const newItem = subDivisions.value.find(item => item.id === resSave.data.id);
      otherDetails.value.subChannel = newItem || '';
    }
    Object.assign(newSubDivision, { name: '', code: '', description: '', division: null });
  } catch (err) {
    console.error("Error adding sub division", err);
    toast('error', 'Failed to add sub division');git 
  }
};





const branches = ref([])
const departments = ref([])
const subDepartments = ref([])
const designations = ref([])
const regions = ref([])
const divisions = ref([])
const subDivisions = ref([])
const categories = ref([])
const employeeGrades = ref([])

// Loading functions
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
      axios.get('http://localhost:9090/api/branches/dropdown'),
      axios.get('http://localhost:9090/api/departments/dropdown'),
      axios.get('http://localhost:9090/api/sub-departments/dropdown'),
      axios.get('http://localhost:9090/api/designations/dropdown'),
      axios.get('http://localhost:9090/api/regions/dropdown'),
      axios.get('http://localhost:9090/api/divisions/dropdown'),
      axios.get('http://localhost:9090/api/sub-divisions/dropdown'),
      axios.get('http://localhost:9090/api/categories/dropdown'),
      axios.get('http://localhost:9090/api/employee-grades/dropdown')
    ])

    branches.value = branchesRes.data
    departments.value = departmentsRes.data
    subDepartments.value = subDepartmentsRes.data
    designations.value = designationsRes.data
    regions.value = regionsRes.data
    divisions.value = divisionsRes.data
    subDivisions.value = subDivisionsRes.data
    categories.value = categoriesRes.data
    employeeGrades.value = employeeGradesRes.data

  } catch (err) {
    console.error('Failed to load master data:', err)
  }
}


const loadSubDepartmentsByDepartment = async (departmentId) => {
  if (!departmentId) {
    subDepartments.value = []
    return
  }
  try {
    const res = await axios.get(`http://localhost:9090/api/sub-departments/dropdown/by-department/${departmentId}`)
    subDepartments.value = res.data
  } catch (err) {
    console.error('Failed to load subdepartments:', err)
    subDepartments.value = []
  }
}

const loadSubDivisionsByDivision = async (divisionId) => {
  if (!divisionId) {
    subDivisions.value = []
    return
  }
  try {
    const res = await axios.get(`http://localhost:9090/api/sub-divisions/dropdown/by-division/${divisionId}`)
    subDivisions.value = res.data
  } catch (err) {
    console.error('Failed to load subdivisions:', err)
    subDivisions.value = []
  }
}

const handleDepartmentChange = (department) => {
  otherDetails.value.subDepartment = '' // Clear subdepartment selection
  if (department && department.id) {
    loadSubDepartmentsByDepartment(department.id)
  }
}

// Watch for division changes to load subdivisions
const handleDivisionChange = (division) => {
  otherDetails.value.subChannel = '' // Clear subdivision selection
  if (division && division.id) {
    loadSubDivisionsByDivision(division.id)
  }
}

onMounted(async () => {
  try {
    const res = await axios.get('http://localhost:9090/api/countries')
    countries.value = res.data
    
    // Load all master data
    await loadMasterData()
  } catch (err) {
    console.error('Failed to load initial data:', err)
  }
})

</script>

<style>
.v-input__prepend {
  display: none !important;
}
/* Hide or override webpack dev server error overlay */
#webpack-dev-server-client-overlay {
  position: unset !important;
  pointer-events: none !important; /* Optional: allow clicks to pass through */
  z-index: -1 !important; /* Push behind everything */
}
</style>
