<template>
  <div>
    <v-row
      dense
      align="center"
    >
      <v-col cols="4">
        <span class="ml-2">{{ name }}</span>
      </v-col>
      <v-col cols="8">
        <v-select
          v-model="localValue"
          :items="indexList"
          item-text="name"
          item-value="index"
          dark
          color="#fcf6de"
          item-color="blue"
          :disabled="disabled"
          @change="$emit('rollback', localValue)"
        />
      </v-col>
    </v-row>
  </div>
</template>

<script>
    export default {
        name: 'Select',
      // eslint-disable-next-line vue/require-prop-types
        props: ['list', 'name', 'value', 'disabled'],
        data() {
            return {}
        },
        computed: {
            localValue: {
                get() {
                    return this.value;
                },
                set(value) {
                    this.$emit('input', value)
                }
            },
            indexList() {
                let list = [];
                for (let i = 0; i < this.list.length; i++) {
                    list.push({
                        name: this.list[i],
                        index: i
                    });
                }
                return list;
            }
        }
    }
</script>

<style>
</style>