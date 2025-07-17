<template>
  <v-app>
    <!-- App Bar - Fixed at top -->
    <v-app-bar
      app
      fixed
      style="background: linear-gradient(to left, #001f3f, #336699); color: white; transition: width 0.2s ease;"
      dark
      elevation="4"
    >
      <v-app-bar-nav-icon @click="togglePinned" />

      <v-toolbar-title class="ml-2 d-flex align-center" style="padding-bottom: 7px;">
        <img src="@/assets/logo.png" alt="Logo" height="30" class="mr-2" />
        Millennium Industries
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-menu offset-y>
        <template #activator="{ props }">
          <v-btn icon v-bind="props">
            <v-icon>mdi-account-circle</v-icon>
          </v-btn>
        </template>

        <v-list>
          <v-list-item @click="loginAsAdmin">
            <v-list-item-title>Login as Admin</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-app-bar>

    <!-- Sidebar - Fixed on left -->
    <v-navigation-drawer
      app
      fixed
      :permanent="true"
      :width="hovering || pinned ? 200 : 50"
      class="sidebar"
      @mouseenter="hovering = true"
      @mouseleave="hovering = false"
    >
      <v-list dense>
        <v-list-item
          v-for="item in menuItems"
          :key="item.title"
          :to="item.route"
          router
          link
          class="nav-item sidebar-item"
        >
          <div class="sidebar-icon-title">
            <v-list-item-icon>
              <v-icon size="20">{{ item.icon }}</v-icon>
            </v-list-item-icon>
            <span v-if="hovering || pinned" class="sidebar-title">
              {{ item.title }}
            </span>
          </div>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <!-- Main Page Content - Scrollable below app bar -->
    <v-main class="main-content">
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
export default {
  data() {
    return {
      hovering: false,
      pinned: false,
      menuItems: [
        { title: "Dashboard", icon: "mdi-view-dashboard", route: "/" },
        { title: "Attendance", icon: "mdi-fingerprint", route: "/attendance" },
        { title: "Employee", icon: "mdi-account-group", route: "/employee" },
        { title: "Reports", icon: "mdi-chart-box", route: "/reports" },
        { title: "Settings", icon: "mdi-cog", route: "/settings" },
      ],
    };
  },
  methods: {
    togglePinned() {
      this.pinned = !this.pinned;
    },
    loginAsAdmin() {
      // Add your login logic here
      console.log("Login as Admin clicked");
    },
  },
};
</script>

<style scoped>
/* Ensure the app takes full height */
.v-app {
  min-height: 100vh;
}

/* Sidebar styling */
.sidebar {
  background-color: #0c1a45 !important;
  color: white;
  transition: width 0.2s ease;
  position: fixed !important;
  top: 0;
  left: 0;
  z-index: 1000;
}

/* Main content area */
.main-content {
  background-color: #f5f5f5;
  min-height: calc(100vh - 64px); /* Subtract app bar height */
  overflow-y: auto;
}

/* Flex row for icon and title */
.sidebar-item .sidebar-icon-title {
  display: flex;
  align-items: center;
}

/* Space between icon and title */
.sidebar-title {
  margin-left: 10px;
  font-size: 16px;
  white-space: nowrap;
  transition: opacity 0.2s ease;
}

.v-list-item {
  color: white !important;
  transition: all 0.2s ease;
  min-height: 48px;
  padding-left: 8px;
}

.v-list-item:hover {
  background-color: #1a2d5a !important;
}

.v-list-item--active {
  background-color: #336699 !important;
}

.v-icon {
  color: white !important;
  font-size: 20px !important;
}

/* App bar specific styling */
.v-app-bar {
  position: fixed !important;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1001;
}

/* Ensure proper spacing for content */
.v-main {
  padding-top: 64px !important; /* App bar height */
}

/* Additional fixes for Vuetify layout */
.v-app-bar--fixed {
  position: fixed !important;
}

.v-navigation-drawer--fixed {
  position: fixed !important;
}
</style>