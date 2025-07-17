<template>
  <v-app>
    <!-- Fixed Top Toolbar -->
    <v-app-bar app style="background: linear-gradient(to left, #001f3f, #336699); /* light to dark navy */
  color: white;
  transition: width 0.2s ease;" dark>
      <v-app-bar-nav-icon @click="togglePinned" />
      <v-toolbar-title class="ml-2 d-flex align-center" style="padding-bottom: 7px;">
        <img src="@/assets/logo.png" alt="Logo" height="30" class="mr-2" />
        Millennium Industries
      </v-toolbar-title>
    </v-app-bar>

    <!-- Sidebar -->
    <v-navigation-drawer
      app
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

    <!-- Main Page Content -->
    <v-main class="fill-height">
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
  },
};
</script>

<style scoped>
.sidebar {
  background-color: #0c1a45;
  color: white;
  transition: width 0.2s ease;
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
}

.v-list-item {
  color: white;
  transition: all 0.2s ease;
  min-height: 48px;
  padding-left: 8px;
}

.v-list-item:hover {
  background-color: #1a2d5a;
}

.v-icon {
  color: white;
  font-size: 20px !important;
}
</style>
