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
            <v-text-field label="Birthday Date" v-model="form.bdayDate" type="date" />
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-text-field label="Date of Joining" v-model="form.dateOfJoining" type="date" />
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
            <v-text-field label="Emergency Contact No" v-model="form.emergency_contact_no" />
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
                        <v-btn color="primary" size="small" class="mt-4" @click="goToNextTab">Next</v-btn>
                    </v-col>
                </v-row>
              </v-form>
            </template>

            <!-- Documents Tab -->
            <template v-else-if="tab === 1">
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
                        <v-btn color="primary" size="small" class="mt-4" @click="goToPrevTab">Previous</v-btn>
                    </v-col>
                    <v-col style="text-align: right;">
                        <v-btn color="primary" size="small" class="mt-4 ml-2" @click="goToNextTab">Next</v-btn>
                    </v-col>
                </v-row>
              </v-form>
            </template>
            <!-- Bank Details Tab (last tab) -->
            <template v-else-if="tab === 2">
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
                        <v-btn color="primary" size="small" class="mt-4" @click="goToPrevTab">Previous</v-btn>
                    </v-col>
                    <v-col style="text-align: right;">
                        <v-btn @click="submitForm" size="small" color="success" class="mt-4 ml-2">Submit</v-btn>
                    </v-col>
                </v-row>
              </v-form>
            </template>
          </div>
        </v-tab-item>
      </v-tabs-items>
    </v-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted  } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const router = useRouter()
const countries = ref([])
const states = ref([])
const cities = ref([])
// const loading = ref(false)
onMounted(async () => {
  try {
    const res = await axios.get('http://localhost:9090/api/countries')
    countries.value = res.data
  } catch (err) {
    console.error('Failed to load countries:', err)
  } finally {
  }
})

function goback() {
  router.push('/employee')
}

const defaultAvatar = 'https://cdn-icons-png.flaticon.com/512/3135/3135715.png'

const form = ref({
  firstName: '',
  lastName: '',
  recruited: 'No',
  username: '',
  empNo: '',
  email: '',
  address1: '',
  address2: '',
  country: '',
  state: '',
  city: '',
  officialNo: '',
  whatsappNo: '',
  bdayDate: '',
  dateOfJoining: '',
  gender: '',
  bloodGroup: '',
  emergencyContactNo: '',
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
    name: `${form.value.firstName} ${form.value.lastName}`,
    address: form.address1 + form.address2,
    countryCode: form.value.country?.code || '',
    stateCode: form.value.state?.code || '',
    cityCode: form.value.city?.code || '',
  }

  console.log("Submitting payload:", payload)

  try {
    const res = await axios.post('http://localhost:9090/api/employee/add', payload)
    console.log("Response:", res)
    alert('Employee added successfully!')
  } catch (err) {
    console.error("Submission error:", err)
    alert('Failed to add employee')
  }
}


const loadStates = async () => {
  // console.log('Loading states for country:', country)
  console.log("Loading", form._rawValue.country.code)
  if (form._rawValue.country) {
    const res = await axios.get(`http://localhost:9090/api/states/by-country-code/${form._rawValue.country.code}`)
    states.value = res.data
  }
}
const loadCities = async () => {
  const stateCode = form._rawValue.state?.code;
  const countryCode = form._rawValue.country?.code;

  if (stateCode && countryCode) {
    try {
      const res = await axios.get('http://localhost:9090/api/cities/by-country-and-state', {
        params: {
          stateCode,
          countryCode
        }
      });
      cities.value = res.data; // or whatever you're using to store city data
    } catch (error) {
      console.error('Error loading cities:', error);
      cities.value = [];
    }
  }
};


// Tabs logic
const tab = ref(0)
const tabItems = [
  { label: 'Other Details', component: null },
  { label: 'Documents', component: null },
  { label: 'Bank Details', component: null }
]

const goToNextTab = () => {
  if (tab.value < tabItems.length - 1) tab.value++
}
const documents = ref([{ name: '', number: '', start: '', expiry: '', file: null }])
const bankDetails = ref([{ bankName: '', accountNo: '', ifsc: '' }])

// Documents
const addDocumentRow = () => { documents.value.push({ name: '', number: '', start: '', expiry: '', file: null }) }
const removeDocumentRow = idx => { if (documents.value.length > 1) documents.value.splice(idx, 1) }
const onDocumentUpload = idx => { /* handle upload logic if needed */ }

// Bank Details
const addBankRow = () => { bankDetails.value.push({ bankName: '', accountNo: '', ifsc: '' }) }
const removeBankRow = idx => { if (bankDetails.value.length > 1) bankDetails.value.splice(idx, 1) }

// Tab navigation
const goToPrevTab = () => { if (tab.value > 0) tab.value-- }

const addBranch = () => {
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
/* Hide or override webpack dev server error overlay */
#webpack-dev-server-client-overlay {
  position: unset !important;
  pointer-events: none !important; /* Optional: allow clicks to pass through */
  z-index: -1 !important;          /* Push behind everything */
}

</style>