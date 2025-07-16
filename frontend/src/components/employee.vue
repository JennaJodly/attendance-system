<template>
  <div class="ma-6">
    <v-card elevation="2" class="pa-6">
      <h2 class="text-h5 font-weight-bold mb-4">Add Employee Details</h2>

      <v-form @submit.prevent="submitForm">
        <v-row dense wrap>
          <!-- Name Fields -->
          <v-col cols="12" sm="6" md="3">
            <v-text-field label="Employee First Name *" v-model="form.firstName" required />
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-text-field label="Employee Last Name" v-model="form.lastName" />
          </v-col>

          <!-- Employee Details -->
          <v-col cols="12" sm="6" md="3">
            <v-text-field label="Username" v-model="form.username" hint="Min 5 & Max 15 characters" />
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-text-field label="Employee No *" v-model="form.employeeId" required />
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
            <v-text-field label="Country" v-model="form.country" />
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-text-field label="State" v-model="form.state" />
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-text-field label="City" v-model="form.city" />
          </v-col>

          <!-- Contact Info -->
          <v-col cols="12" sm="6" md="3">
            <v-text-field label="Official No 1" v-model="form.officialNo" />
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-text-field label="WhatsApp No" v-model="form.whatsapp" />
          </v-col>

          <!-- Important Dates -->
          <v-col cols="12" sm="6" md="3">
            <v-text-field label="Birthday Date" v-model="form.birthday" type="date" />
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-text-field label="Date of Joining" v-model="form.joiningDate" type="date" />
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
            <v-text-field label="Emergency Contact No" v-model="form.emergencyContact" />
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
      <v-tabs-items v-model="tab">
        <v-tab-item>
          <div class="pa-4">
            <!-- Other Details Tab -->
            <template v-if="tab === 0">
              <v-form>
                <v-row dense>
                  <v-col cols="12" sm="6" md="3">
                    <div class="d-flex align-center">
                      <v-autocomplete
                        label="Branch *"
                        :items="[]"
                        v-model="otherDetails.branch"
                      />
                      <v-btn icon color="primary" class="ml-2" @click="addBranch" density="compact" style="height:28px;width:28px;min-width:28px;">
                        <v-icon size="18">mdi-plus</v-icon>
                      </v-btn>
                    </div>
                  </v-col>
                  <v-col cols="12" sm="6" md="3">
                    <div class="d-flex align-center">
                      <v-autocomplete
                        label="Department *"
                        :items="[]"
                        v-model="otherDetails.department"
                      />
                      <v-btn icon color="primary" class="ml-2" @click="addDepartment" density="compact" style="height:28px;width:28px;min-width:28px;">
                        <v-icon size="18">mdi-plus</v-icon>
                      </v-btn>
                    </div>
                  </v-col>
                  <v-col cols="12" sm="6" md="3">
                    <div class="d-flex align-center">
                      <v-autocomplete
                        label="Sub Department"
                        :items="[]"
                        v-model="otherDetails.subDepartment"
                      />
                      <v-btn icon color="primary" class="ml-2" @click="addSubDepartment" density="compact" style="height:28px;width:28px;min-width:28px;">
                        <v-icon size="18">mdi-plus</v-icon>
                      </v-btn>
                    </div>
                  </v-col>
                  <v-col cols="12" sm="6" md="3">
                    <div class="d-flex align-center">
                      <v-autocomplete label="Designation *" :items="[]" v-model="otherDetails.designation" />
                      <v-btn icon color="primary" class="ml-2" @click="addDesignation" density="compact" style="height:28px;width:28px;min-width:28px;">
                        <v-icon size="18">mdi-plus</v-icon>
                      </v-btn>
                    </div>
                  </v-col>
                  <v-col cols="12" sm="6" md="3">
                    <div class="d-flex align-center">
                      <v-autocomplete label="Region" :items="[]" v-model="otherDetails.region" />
                      <v-btn icon color="primary" class="ml-2" @click="addRegion" density="compact" style="height:28px;width:28px;min-width:28px;">
                        <v-icon size="18">mdi-plus</v-icon>
                      </v-btn>
                    </div>
                  </v-col>
                  <v-col cols="12" sm="6" md="3">
                    <div class="d-flex align-center">
                      <v-autocomplete label="Channel/Division" :items="[]" v-model="otherDetails.channelDivision" />
                      <v-btn icon color="primary" class="ml-2" @click="addChannelDivision" density="compact" style="height:28px;width:28px;min-width:28px;">
                        <v-icon size="18">mdi-plus</v-icon>
                      </v-btn>
                    </div>
                  </v-col>
                  <v-col cols="12" sm="6" md="3">
                    <div class="d-flex align-center">
                      <v-autocomplete label="Sub Channel/Sub Division" :items="[]" v-model="otherDetails.subChannel" />
                      <v-btn icon color="primary" class="ml-2" @click="addSubChannel" density="compact" style="height:28px;width:28px;min-width:28px;">
                        <v-icon size="18">mdi-plus</v-icon>
                      </v-btn>
                    </div>
                  </v-col>
                  <v-col cols="12" sm="6" md="3">
                    <div class="d-flex align-center">
                      <v-autocomplete label="Category/Product" :items="[]" v-model="otherDetails.categoryProduct" />
                      <v-btn icon color="primary" class="ml-2" @click="addCategoryProduct" density="compact" style="height:28px;width:28px;min-width:28px;">
                        <v-icon size="18">mdi-plus</v-icon>
                      </v-btn>
                    </div>
                  </v-col>
                  <v-col cols="12" sm="6" md="3">
                    <div class="d-flex align-center">
                      <v-autocomplete label="Employee Grade" :items="[]" v-model="otherDetails.employeeGrade" />
                      <v-btn icon color="primary" class="ml-2" @click="addEmployeeGrade" density="compact" style="height:28px;width:28px;min-width:28px;">
                        <v-icon size="18">mdi-plus</v-icon>
                      </v-btn>
                    </div>
                  </v-col>
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
                        <v-btn color="primary" class="mt-4" @click="goToNextTab">Next</v-btn>
                    </v-col>
                </v-row>
              </v-form>
            </template>
            <!-- Statutory Info Tab -->
            <template v-else-if="tab === 1">
              <v-form>
                <v-row dense v-for="(stat, idx) in statutoryInfo" :key="idx" class="align-center mb-2">
                  <v-col cols="12" sm="3">
                    <v-autocomplete
                      label="Name"
                      :items="statutoryNameOptions"
                      v-model="stat.name"
                      dense
                      required
                    />
                  </v-col>
                  <v-col cols="12" sm="3">
                    <v-text-field
                      label="Registration No"
                      v-model="stat.registrationNo"
                      dense
                      required
                    />
                  </v-col>
                  <v-col cols="12" sm="3">
                    <v-file-input
                      label="Document Upload"
                      v-model="stat.document"
                      dense
                      show-size
                      @change="onStatutoryFileChange($event, idx)"
                    />
                  </v-col>
                  <v-col cols="12" sm="2">
                    <v-text-field
                      label="Expiry if relevant"
                      v-model="stat.expiry"
                      type="date"
                      dense
                    />
                  </v-col>
                  <v-col cols="12" sm="1" class="d-flex align-center">
                    <v-btn icon class="mr-2" color="success"  density="compact" size="small" @click="addStatutoryRow" v-if="idx === statutoryInfo.length - 1">
                      <v-icon size="18">mdi-plus-circle</v-icon>
                    </v-btn>
                    <v-btn icon color="error" density="compact" size="small" @click="removeStatutoryRow(idx)" v-if="statutoryInfo.length > 1">
                      <v-icon size="18">mdi-minus-circle</v-icon>
                    </v-btn>
                  </v-col>
                </v-row>
                <v-row style="justify-content: space-between;">
                    <v-col>
                        <v-btn color="primary" class="mt-4" @click="goToPrevTab">Previous</v-btn>
                    </v-col>
                    <v-col style="text-align: right;">
                        <v-btn color="primary" class="mt-4 ml-2" @click="goToNextTab">Next</v-btn>
                    </v-col>
                </v-row>
                
              </v-form>
            </template>
            <!-- Documents Tab -->
            <template v-else-if="tab === 2">
              <v-form>
                <v-row dense v-for="(doc, idx) in documents" :key="idx" class="align-center mb-2">
                  <v-col cols="12" sm="3">
                    <v-text-field label="Document Name" v-model="doc.name" dense />
                  </v-col>
                  <v-col cols="12" sm="2">
                    <v-text-field label="Document No" v-model="doc.number" dense />
                  </v-col>
                  <v-col cols="12" sm="2">
                    <v-text-field label="Start from" v-model="doc.start" type="date" dense />
                  </v-col>
                  <v-col cols="12" sm="2">
                    <v-text-field label="Expiry date" v-model="doc.expiry" type="date" dense />
                  </v-col>
                  <v-col cols="12" sm="1">
                    <v-btn icon @click="onDocumentUpload(idx)">
                      <v-icon>mdi-upload</v-icon>
                    </v-btn>
                  </v-col>
                  <v-col cols="12" sm="2" class="d-flex align-center">
                    <v-btn icon color="success" class="mr-2" density="compact" size="small" @click="addDocumentRow" v-if="idx === documents.length - 1">
                      <v-icon size="18">mdi-plus-circle</v-icon>
                    </v-btn>
                    <v-btn icon color="error" density="compact" size="small" @click="removeDocumentRow(idx)" v-if="documents.length > 1">
                      <v-icon size="18">mdi-minus-circle</v-icon>
                    </v-btn>
                  </v-col>
                </v-row>
                <!-- <v-btn color="primary" class="mt-4" @click="goToPrevTab">Previous</v-btn>
                <v-btn color="primary" class="mt-4 ml-2" @click="goToNextTab">Next</v-btn> -->
                <v-row style="justify-content: space-between;">
                    <v-col>
                        <v-btn color="primary" class="mt-4" @click="goToPrevTab">Previous</v-btn>
                    </v-col>
                    <v-col style="text-align: right;">
                        <v-btn color="primary" class="mt-4 ml-2" @click="goToNextTab">Next</v-btn>
                    </v-col>
                </v-row>
              </v-form>
            </template>
            <!-- Experience Tab -->
            <template v-else-if="tab === 3">
              <v-form>
                <v-row dense v-for="(exp, idx) in experience" :key="idx" class="align-center mb-2">
                  <v-col cols="12" sm="2">
                    <v-text-field label="Company Name" v-model="exp.company" dense />
                  </v-col>
                  <v-col cols="12" sm="2">
                    <v-text-field label="From Date" v-model="exp.from" type="date" dense />
                  </v-col>
                  <v-col cols="12" sm="2">
                    <v-text-field label="To Date" v-model="exp.to" type="date" dense />
                  </v-col>
                  <v-col cols="12" sm="2">
                    <v-text-field label="Designation" v-model="exp.designation" dense />
                  </v-col>
                  <v-col cols="12" sm="3">
                    <v-text-field label="Designation in brief" v-model="exp.brief" dense />
                  </v-col>
                  <v-col cols="12" sm="1" class="d-flex align-center">
                    <v-btn icon color="success" class="mr-2" density="compact" size="small" @click="addExperienceRow" v-if="idx === experience.length - 1">
                      <v-icon size="18">mdi-plus-circle</v-icon>
                    </v-btn>
                    <v-btn icon color="error" density="compact" size="small" @click="removeExperienceRow(idx)" v-if="experience.length > 1">
                      <v-icon size="18">mdi-minus-circle</v-icon>
                    </v-btn>
                  </v-col>
                </v-row>
                <!-- <v-btn color="primary" class="mt-4" @click="goToPrevTab">Previous</v-btn>
                <v-btn color="primary" class="mt-4 ml-2" @click="goToNextTab">Next</v-btn> -->
                <v-row style="justify-content: space-between;">
                    <v-col>
                        <v-btn color="primary" class="mt-4" @click="goToPrevTab">Previous</v-btn>
                    </v-col>
                    <v-col style="text-align: right;">
                        <v-btn color="primary" class="mt-4 ml-2" @click="goToNextTab">Next</v-btn>
                    </v-col>
                </v-row>
              </v-form>
            </template>
            <!-- Increment Details Tab -->
            <template v-else-if="tab === 4">
              <v-form>
                <v-row dense>
                  <v-col cols="12" sm="3">
                    <v-text-field label="Last Increment Date" v-model="incrementDetails.lastIncrementDate" type="date" dense />
                  </v-col>
                  <v-col cols="12" sm="3">
                    <v-text-field label="Last Increment Amount" v-model="incrementDetails.lastIncrementAmount" dense />
                  </v-col>
                  <v-col cols="12" sm="3">
                    <v-text-field label="Last Promotion Date" v-model="incrementDetails.lastPromotionDate" type="date" dense />
                  </v-col>
                  <v-col cols="12" sm="3">
                    <v-text-field label="Next Promotion Due On" v-model="incrementDetails.nextPromotionDueOn" type="date" dense />
                  </v-col>
                  <v-col cols="12" sm="3">
                    <v-text-field label="Last Promotion Date" v-model="incrementDetails.lastPromotionDate2" type="date" dense />
                  </v-col>
                  <v-col cols="12" sm="3">
                    <v-text-field label="Training Due On" v-model="incrementDetails.trainingDueOn" type="date" dense />
                  </v-col>
                  <v-col cols="12" sm="3">
                    <v-text-field label="Reason" v-model="incrementDetails.reason" dense />
                  </v-col>
                  <v-col cols="12" sm="3">
                    <v-text-field label="Reprimanded From" v-model="incrementDetails.reprimandedFrom" type="date" dense />
                  </v-col>
                  <v-col cols="12" sm="3">
                    <v-text-field label="Reprimanded To" v-model="incrementDetails.reprimandedTo" type="date" dense />
                  </v-col>
                </v-row>
                <!-- <v-btn color="primary" class="mt-4" @click="goToPrevTab">Previous</v-btn>
                <v-btn color="primary" class="mt-4 ml-2" @click="goToNextTab">Next</v-btn> -->
                <v-row style="justify-content: space-between;">
                    <v-col>
                        <v-btn color="primary" class="mt-4" @click="goToPrevTab">Previous</v-btn>
                    </v-col>
                    <v-col style="text-align: right;">
                        <v-btn color="primary" class="mt-4 ml-2" @click="goToNextTab">Next</v-btn>
                    </v-col>
                </v-row>
              </v-form>
            </template>
            <!-- Investment Declaration Tab -->
            <template v-else-if="tab === 5">
              <v-form>
                <v-row dense v-for="(inv, idx) in investments" :key="idx" class="align-center mb-2">
                  <v-col cols="12" sm="3">
                    <v-text-field label="Policy Name" v-model="inv.policyName" dense />
                  </v-col>
                  <v-col cols="12" sm="2">
                    <v-text-field label="Start Date" v-model="inv.startDate" type="date" dense />
                  </v-col>
                  <v-col cols="12" sm="2">
                    <v-text-field label="End Date" v-model="inv.endDate" type="date" dense />
                  </v-col>
                  <v-col cols="12" sm="2">
                    <v-text-field label="Amount" v-model="inv.amount" dense />
                  </v-col>
                  <v-col cols="12" sm="1" class="d-flex align-center">
                    <v-btn icon color="success" class="mr-2" density="compact" size="small" @click="addInvestmentRow" v-if="idx === investments.length - 1">
                      <v-icon size="18">mdi-plus-circle</v-icon>
                    </v-btn>
                    <v-btn icon color="error" density="compact" size="small" @click="removeInvestmentRow(idx)" v-if="investments.length > 1">
                      <v-icon size="18">mdi-minus-circle</v-icon>
                    </v-btn>
                  </v-col>
                </v-row>
                <!-- <v-btn color="primary" class="mt-4" @click="goToPrevTab">Previous</v-btn>
                <v-btn color="primary" class="mt-4 ml-2" @click="goToNextTab">Next</v-btn> -->
                <v-row style="justify-content: space-between;">
                    <v-col>
                        <v-btn color="primary" class="mt-4" @click="goToPrevTab">Previous</v-btn>
                    </v-col>
                    <v-col style="text-align: right;">
                        <v-btn color="primary" class="mt-4 ml-2" @click="goToNextTab">Next</v-btn>
                    </v-col>
                </v-row>
              </v-form>
            </template>
            <!-- Bank Details Tab (last tab) -->
            <template v-else-if="tab === 6">
              <v-form @submit.prevent="submitForm">
                <v-row dense v-for="(bank, idx) in bankDetails" :key="idx" class="align-center mb-2">
                  <v-col cols="12" sm="3">
                    <v-text-field label="Bank Name" v-model="bank.bankName" dense />
                  </v-col>
                  <v-col cols="12" sm="3">
                    <v-text-field label="Account No" v-model="bank.accountNo" dense />
                  </v-col>
                  <v-col cols="12" sm="3">
                    <v-text-field label="IFSC code" v-model="bank.ifsc" dense />
                  </v-col>
                  <v-col cols="12" sm="1" class="d-flex align-center">
                    <v-btn icon color="success" class="mr-2" density="compact" size="small" @click="addBankRow" v-if="idx === bankDetails.length - 1">
                        <v-icon size="18">mdi-plus-circle</v-icon>
                        </v-btn>
                        <v-btn icon color="error" density="compact" size="small" @click="removeBankRow(idx)" v-if="bankDetails.length > 1">
                        <v-icon size="18">mdi-minus-circle</v-icon>
                        </v-btn>
                  </v-col>
                </v-row>
                <!-- <v-btn color="primary" class="mt-4" @click="goToPrevTab">Previous</v-btn>
                <v-btn type="submit" color="success" class="mt-4 ml-2">Submit</v-btn> -->
                <v-row style="justify-content: space-between;">
                    <v-col>
                        <v-btn color="primary" class="mt-4" @click="goToPrevTab">Previous</v-btn>
                    </v-col>
                    <v-col style="text-align: right;">
                        <v-btn type="submit" color="success" class="mt-4 ml-2">Submit</v-btn>
                    </v-col>
                </v-row>
              </v-form>
            </template>
            <!-- Fallback for other tabs -->
            <template v-else>
              <p>{{ tabItems[tab].label }} content goes here.</p>
            </template>
          </div>
        </v-tab-item>
      </v-tabs-items>
    </v-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import axios from 'axios'

const defaultAvatar = 'https://cdn-icons-png.flaticon.com/512/3135/3135715.png'

const form = ref({
  firstName: '',
  lastName: '',
  recruited: 'No',
  username: '',
  employeeId: '',
  email: '',
  address1: '',
  address2: '',
  country: '',
  state: '',
  city: '',
  officialNo: '',
  whatsapp: '',
  birthday: '',
  joiningDate: '',
  gender: '',
  bloodGroup: '',
  emergencyContact: '',
  photo: ''
})

const otherDetails = ref({
  branch: '',
  department: '',
  subDepartment: '',
  designation: '',
  region: '',
  channelDivision: '',
  subChannel: '',
  categoryProduct: '',
  employeeGrade: '',
  reportingTo: '',
  reportingToError: false,
  reportingToErrorMsg: 'Reporting To is required',
  noOfAppraisers: 1,
  appraiser1: '',
  appraiser2: '', // add this line
  reviewer: '',
  toBeReplacedFor: '',
  effectiveFrom: ''
})

// Statutory Info logic
const statutoryNameOptions = [
  'Aadhaar', 'Voter', 'PAN', 'Passport', 'Driving License', 'ESIC', 'PF', 'Other'
]
const statutoryInfo = ref([
  { name: '', registrationNo: '', document: null, expiry: '' }
])

const addStatutoryRow = () => {
  statutoryInfo.value.push({ name: '', registrationNo: '', document: null, expiry: '' })
}
const removeStatutoryRow = (idx) => {
  if (statutoryInfo.value.length > 1) statutoryInfo.value.splice(idx, 1)
}
const onStatutoryFileChange = (e, idx) => {
  const file = e.target?.files?.[0] || e
  if (file) {
    statutoryInfo.value[idx].document = file
  }
}
const saveStatutoryInfo = () => {
  // Save logic: send statutoryInfo.value to backend or merge with main form
  // Example: console.log('Saving statutory info', statutoryInfo.value)
  alert('Statutory Info saved!')
}

const handlePhotoUpload = (e) => {
  const file = e.target.files[0]
  const reader = new FileReader()
  reader.onloadend = () => {
    form.value.photo = reader.result
  }
  if (file) reader.readAsDataURL(file)
}

const submitForm = async () => {
  const payload = {
    ...form.value,
    name: `${form.value.firstName} ${form.value.lastName}`
  }

  try {
    await axios.post('http://localhost:8080/api/users/add', payload)
    alert('Employee added successfully!')
    // reset form or navigate
  } catch (err) {
    console.error(err)
    alert('Failed to add employee')
  }
}

// Tabs logic
const tab = ref(0)
const tabItems = [
  { label: 'Other Details', component: null },
  { label: 'Statutory Info', component: null },
  { label: 'Documents', component: null },
  { label: 'Experience', component: null },
  { label: 'Increment Details', component: null },
  { label: 'Investment Declaration', component: null },
  { label: 'Bank Details', component: null }
]

const goToNextTab = () => {
  if (tab.value < tabItems.length - 1) tab.value++
}
const documents = ref([{ name: '', number: '', start: '', expiry: '', file: null }])
const experience = ref([{ company: '', from: '', to: '', designation: '', brief: '' }])
const investments = ref([{ policyName: '', startDate: '', endDate: '', amount: '' }])
const bankDetails = ref([{ bankName: '', accountNo: '', ifsc: '' }])
const incrementDetails = ref({
  lastIncrementDate: '', lastIncrementAmount: '', lastPromotionDate: '',
  nextPromotionDueOn: '', lastPromotionDate2: '', trainingDueOn: '',
  reason: '', reprimandedFrom: '', reprimandedTo: ''
})

// Documents
const addDocumentRow = () => { documents.value.push({ name: '', number: '', start: '', expiry: '', file: null }) }
const removeDocumentRow = idx => { if (documents.value.length > 1) documents.value.splice(idx, 1) }
const onDocumentUpload = idx => { /* handle upload logic if needed */ }

// Experience
const addExperienceRow = () => { experience.value.push({ company: '', from: '', to: '', designation: '', brief: '' }) }
const removeExperienceRow = idx => { if (experience.value.length > 1) experience.value.splice(idx, 1) }

// Investments
const addInvestmentRow = () => { investments.value.push({ policyName: '', startDate: '', endDate: '', amount: '' }) }
const removeInvestmentRow = idx => { if (investments.value.length > 1) investments.value.splice(idx, 1) }

// Bank Details
const addBankRow = () => { bankDetails.value.push({ bankName: '', accountNo: '', ifsc: '' }) }
const removeBankRow = idx => { if (bankDetails.value.length > 1) bankDetails.value.splice(idx, 1) }

// Tab navigation
const goToPrevTab = () => { if (tab.value > 0) tab.value-- }

const addBranch = () => {
  // logic to add a branch (e.g., open dialog or prompt)
  alert('Add Branch clicked');
};
const addDepartment = () => {
  alert('Add Department clicked');
};
const addSubDepartment = () => {
  alert('Add Sub Department clicked');
};
const addDesignation = () => { alert('Add Designation clicked'); };
const addRegion = () => { alert('Add Region clicked'); };
const addChannelDivision = () => { alert('Add Channel/Division clicked'); };
const addSubChannel = () => { alert('Add Sub Channel/Sub Division clicked'); };
const addCategoryProduct = () => { alert('Add Category/Product clicked'); };
const addEmployeeGrade = () => { alert('Add Employee Grade clicked'); };
</script>

<style>
.v-input__prepend {
  display: none !important;
}
</style>