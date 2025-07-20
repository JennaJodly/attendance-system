export default {
  // Example global function
  formatFullName(firstName, lastName) {
    return `${firstName} ${lastName}`;
  },

  // Another utility function
  capitalize(str) {
    return str.charAt(0).toUpperCase() + str.slice(1);
  },

  // Toast function if needed
  global_push_toast(type, message) {
        const toast = app.config.globalProperties.$toast;
        if (!toast) {
          console.warn("Toast plugin not available.");
          return;
        }

        if (toast.clear) toast.clear(); // Optional: clear previous toasts

        const config = {
          position: "top-right",
          timeout: 5000,
          closeOnClick: true,
          pauseOnFocusLoss: true,
          pauseOnHover: true,
          draggable: true,
          draggablePercent: 0.6,
          showCloseButtonOnHover: false,
          hideProgressBar: true,
          closeButton: "button",
          icon: true,
          rtl: false,
        };

        switch (type) {
          case "success":
            toast.success(message, config);
            break;
          case "error":
            toast.error(message, config);
            break;
          case "info":
            toast.info(message, config);
            break;
          case "warning":
            toast.warning(message, config);
            break;
          default:
            toast(message, config);
        }
      },

      // ... your existing utility methods remain unchanged
      isEmptyObject_Global(obj) {
        return (
          obj &&
          Object.keys(obj).length === 0 &&
          Object.getPrototypeOf(obj) === Object.prototype
        );
      },


      
       common_uppercase(val) {
      if (this[val] != null && this[val] !== "") {
        this[val] = this[val].toUpperCase();
      }
    },
    global_removeByAttr(arrmain, attr, value) {
      const arr = [...arrmain];
      var i = arr.length;
      while (i--) {
        if (
          arr[i] &&
          arr[i].hasOwnProperty(attr) &&
          arguments.length > 2 && arr[i][attr] === value
        ) {
          arr.splice(i, 1);
        }
      }
      return arr;
    },
    to_checkBy_null_conditions(val) {
      if (
        !val ||
        val == NaN ||
        val == Infinity ||
        val == "Infinity" ||
        val == -Infinity
      ) {
        return Number.parseFloat(0);
      } else {
        return Number.parseFloat(val);
      }
    },
    gettingValueArr(arrmain, column) {
      const arr = [...arrmain];
      let retArr = [];
      retArr = arr.map((a) => a[column]);
      retArr = retArr.filter(function(el) {
        return el !== "" && el != null;
      });
      retArr = retArr.map((v) => v.trim().toLowerCase());
      return retArr;
    },

    gettingNumberArr(arrmain, column) {
      const arr = [...arrmain];
      let retArr = [];
      retArr = arr.map((a) => a[column]);
      retArr = retArr.filter(function(el) {
        return el !== "" && el != null;
      });
      // retArr = retArr.map(v => v.trim().toLowerCase());
      return retArr;
    },

    global_totalCalculator(key, dataset) {
      let value = dataset.reduce((a, b) => Number(a) + Number(b[key] || 0), 0);
      value = value.toFixed(2);
      value = Number(value);
      return value;
    },
    global_tofixed(value, digits = 2) {
      if (isNaN(value)) {
        return 0.0;
      } else {
        value = value.toFixed(digits);
        value = Number(value);
        return value;
      }
    },
    isNumber: function(evt) {
      evt = evt ? evt : window.event;
      var charCode = evt.which ? evt.which : evt.keyCode;
      if (charCode == 46) {
        return true;
      }
      if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return evt.preventDefault();
      }

      return true;
    },
    common_fn_distinct_array(dataset = [], distinct_by = []) {
      var seen = Object.create(null),
        result = [];
      result = dataset.filter((o) => {
        var key = distinct_by.map((k) => o[k]).join("|");
        if (!seen[key]) {
          seen[key] = true;
          return true;
        }
      });
      return result || [];
    },
    isNumber_allow_negative: function(evt) {
      evt = evt ? evt : window.event;
      var charCode = evt.which ? evt.which : evt.keyCode;
      if (charCode == 46 || charCode == 45) {
        return true;
      }
      if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return evt.preventDefault();
      }

      return true;
    },
    addserialnumber(array, key, max = 0) {
      let result = array.map((o) => Object.assign({}, o, { [key]: ++max }));

      return result;
    },
    global_sumField(variablename, key, tofixed = 2) {
      let val = this?.[variablename].reduce((a, b) => a + (b[key] * 1 || 0), 0);
      return val.toFixed(tofixed);
    },
    fixtwo_Values_amt(val) {
      var currencyFormatter = new Intl.NumberFormat("en-IN", {
        maximumFractionDigits: 2,
        minimumFractionDigits: 2,
      });
      if (localStorage.getItem('domain') == 'orodxb') {
        currencyFormatter = new Intl.NumberFormat("en-US", {
          maximumFractionDigits: 2,
          minimumFractionDigits: 2,
        });
      }

      if (!val) {
        return Number.parseFloat(0).toFixed(2);
      } else {
        let n = parseFloat(val).toFixed(2);
        return currencyFormatter.format(n);
      }
    },
    toRound(value, decimal = 2) {
      value = value || 0;
      value = value ? value.toFixed(decimal).toString() : 0;
      if (value.indexOf(".") == -1) value += ".";
      while (value.length <= value.indexOf(".") + decimal) value += "0";
      return value;
    },
    trim_lower_case(des = null) {
      if (typeof des == "string" && des) {
        let description = des.trim().toLowerCase();
        return description;
      } else {
        return "";
      }
    },
    return_max_number(arr = [], key = null) {
      return arr.reduce((maxSno, current) => Math.max(maxSno, current[key]), 0) + 1;
    },
    screen_auth(page_code = "", page_type = "") {
      var screen_right_dataset = JSON.parse(
        localStorage.getItem("USER_SCREEN_LIST")
      );
      var page_flg = false;

      if (
        screen_right_dataset != null &&
        screen_right_dataset != "" &&
        screen_right_dataset != undefined &&
        screen_right_dataset.length != 0
      ) {
        screen_right_dataset = screen_right_dataset.filter(function(e) {
          return e.screen_code == page_code;
        });
        for (let i = 0; i < screen_right_dataset.length; i++) {
          if (screen_right_dataset[i].screen_code == page_code) {
            page_flg = screen_right_dataset[i][page_type];
            if (page_flg) {
              break;
            }
          }
        }
      }
      return page_flg;
    },
    module_auth(page_code = "", page_type = "") {
      var module_right_dataset = JSON.parse(
        localStorage.getItem("USER_MODULE_LIST")
      );
      var page_flg = false;
      if (
        module_right_dataset != null &&
        module_right_dataset != "" &&
        module_right_dataset != undefined &&
        module_right_dataset.length != 0
      ) {
        module_right_dataset = module_right_dataset.filter(function(e) {
          return e.module_code == page_code;
        });
        for (let i = 0; i < module_right_dataset.length; i++) {
          if (module_right_dataset[i].module_code == page_code) {
            page_flg = module_right_dataset[i].active;
            if (page_flg) {
              break;
            }
          }
        }
      }
      return page_flg;
    },
    fixthree_Values_amt(val) {
      var currencyFormatter = new Intl.NumberFormat("en-IN", {
        maximumFractionDigits: 3,
        minimumFractionDigits: 3,
      });
      if (localStorage.getItem('domain') == 'orodxb') {
        currencyFormatter = new Intl.NumberFormat("en-US", {
          maximumFractionDigits: 3,
          minimumFractionDigits: 3,
        });
      }
      if (!val) {
        return Number.parseFloat(0).toFixed(3);
      } else {
        let n = parseFloat(val).toFixed(3);
        return currencyFormatter.format(n);
      }
    },
    transfer_to_dataset_for_wo(dataset = [], process = null) {
      let data = ["qc", "hallmarking", "scanning", "laser", "stores"].filter(
        (e) => e != process
      );
      if (process == "hallmarking") {
        data = [
          "qc",
          "hallmarking",
          "scanning",
          "laser",
          "stores",
          "non tag",
        ].filter((e) => e != process);
      }
      let result = dataset.filter((e) => {
        return e.name ? data.includes(e.name.toLowerCase()) : false;
      });
      return result;
    },
    get_aws_pdf_concat_link(filename = null) {
      return filename == null
        ? null
        : filename.slice(0, 8) == "item_img"
        ? "vbj/uploads/original_" + filename
        : "vbj/uploads/" + filename;
    },
    array_sumField(array, key, tofixed = 2) {
      let val = array.reduce((a, b) => a + (b[key] * 1 || 0), 0);
      return val.toFixed(tofixed);
    },
    is_Authorization_need(transaction_type = null) {
      var lvl_dt = JSON.parse(localStorage.getItem("APP_AUTHORIZATION_LVL"));
      let tempArr = lvl_dt.filter(function(e) {
        return e.transaction_type == transaction_type;
      });
      return tempArr.length
        ? Number(tempArr[0].records_count) > 0
          ? true
          : false
        : false;
    },
    dateIFValid: (dt, format) => {
      let date = moment(dt);
      return date.isValid() ? moment(date).format(format) : null;
    },
    dateIFValid_CD: (dt, format) => {
      // if Null then Return with Current Date
      let date = moment(dt);
      return date.isValid()
        ? moment(date).format(format)
        : moment().format(format);
    },
    global_getNowTime(prefixValue = "__unique__") {
      const generateRandomString = (length = 10) =>
        Math.random()
          .toString(20)
          .substr(2, length);
      const today = new Date();
      const date =
        today.getFullYear() +
        "_" +
        (today.getMonth() + 1) +
        "_" +
        today.getDate();
      const time =
        today.getHours() +
        "_" +
        today.getMinutes() +
        "_" +
        today.getSeconds() +
        "_" +
        today.getMilliseconds();
      const dateTime =
        prefixValue + "_" + date + "_" + time + "__" + generateRandomString();
      return dateTime;
    },
    global_totalCalculator_no_fixed(key, dataset) {
      let value = dataset.reduce((a, b) => Number(a) + Number(b[key] || 0), 0);
      value = Number(value);
      return value;
    },
    ifnull(vals) {
      return vals != null ? vals.trim() : "";
    },
    ExcelDownloadFromBASE64(_FileName, _EnCode64) {
      var link = document.createElement("a");
      link.href =
        "data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64," +
        _EnCode64;
      link.setAttribute("download", `${_FileName}.xlsx`);
      link.style.display = "none";
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    },
    sumFieldAll(vrname, key) {
      let val = this[vrname].reduce((a, b) => a + (b[key] * 1 || 0), 0);
      return val;
    },

     fixtwo_Values_amt_(val) {
        if (!val) {
            return Number.parseFloat(0).toFixed(3);
        } else {
            var flags = localStorage.getItem("DISPLAY_FORMAT") ? localStorage.getItem("DISPLAY_FORMAT") : "whole_number";
            if (flags == "thousands") val = this.comma_seperated_val((Number(val) / 1e3).toFixed(3)) + " K";
            else if (flags == "lakhs") val = this.comma_seperated_val((Number(val) / 1e5).toFixed(3)) + " L";
            else if (flags == "millions") val = this.comma_seperated_val((Number(val) / 1e6).toFixed(3)) + " M";
            else if (flags == "crore") val = this.comma_seperated_val((Number(val) / 1e7).toFixed(3)) + " Cr";
            else val = this.comma_seperated_val(Number(val).toFixed(3));
            return val;
        }
        },

      comma_seperated_val(val) {
          var flag = localStorage.getItem("COMMA_FORMAT") ? localStorage.getItem("COMMA_FORMAT") : "lakhs";
          if (flag == "millions") {
              return new Intl.NumberFormat("en-US", {
                  maximumFractionDigits: 3,
                  minimumFractionDigits: 3,
              }).format(val);
          } else {
              return new Intl.NumberFormat("en-IN", {
                  maximumFractionDigits: 3,
                  minimumFractionDigits: 3,
              }).format(val);
          }
        },

        fixtwo_Values(val) {
            if (!val || val == NaN || val == Infinity || val == "Infinity" || val == -Infinity) {
                return Number.parseFloat(0).toFixed(2);
            } else {
                return Number.parseFloat(val).toFixed(2);
            }
        },





};
