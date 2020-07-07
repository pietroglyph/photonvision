<template>
  <div>
    <v-container fluid>
      <v-row align="center" justify="center" class="pa-4">
        <v-col cols="12" lg="8" align-self="stretch">
            <v-card color="primary" height="100%" dark>
                <v-card-title class="pb-0 mb-0 pl-2 pt-1" style="height: 10%;">Cameras</v-card-title>
                <v-row align="center" style="height: 90%;">
<!--                    <v-col cols="12" md="6" class="hidden-sm-and-down">-->
<!--                        <div style="position: relative; width: 100%; height: 100%;">-->
<!--                            <cvImage-->
<!--                                    address="http://localhost:8000/noStream.png"-->
<!--                                    :scale="100"-->
<!--                                    @click="onImageClick"-->
<!--                            />-->
<!--                            <span style="position: absolute; top: 2%; left: 2%; font-size: 28px; -webkit-text-stroke: 1px black;">{{ parseFloat(fps).toFixed(2) }}</span>-->
<!--                        </div>-->
<!--                    </v-col>-->
                    <template v-for="idx in selectedOutputs">
                        <v-col cols="12" v-bind:md="selectedOutputs.length === 0 ? 12 : Math.floor(12 / selectedOutputs.length)">
                            <div style="position: relative; width: 100%; height: 100%;">
                                <cvImage
                                        :address="'http://localhost:8000/noStream.png?idx=' + idx"
                                        :scale="100"
                                        @click="onImageClick"
                                />
                                <span style="position: absolute; top: 2%; left: 2%; font-size: 28px; -webkit-text-stroke: 1px black;">{{ parseFloat(fps).toFixed(2) }}</span>
                            </div>
                        </v-col>
                    </template>
                </v-row>
            </v-card>
        </v-col>
        <v-col cols="12" lg="4" align-self="stretch">
          <v-card color="primary" class="mb-4">
            <camera-and-pipeline-select />
          </v-card>
          <v-card color="primary">
            <v-row align="center" justify="space-center" class="pl-3 pr-3">
                <v-col lg="12">
                    <p style="color: white;">Processing mode:</p>
                    <v-btn-toggle mandatory dark class="fill">
                        <v-btn color="secondary">
                            <v-icon>mdi-cube-outline</v-icon>
                            <span>3D</span>
                        </v-btn>
                        <v-btn color="secondary">
                            <v-icon>mdi-crop-square</v-icon>
                            <span>2D</span>
                        </v-btn>
                    </v-btn-toggle>
                </v-col>
                <v-col lg="12">
                    <p style="color: white;">Stream display:</p>
                    <v-btn-toggle v-model="selectedOutputs" :multiple="$vuetify.breakpoint.mdAndUp" mandatory dark class="fill">
                        <v-btn color="secondary" class="fill">
                            <v-icon>mdi-palette</v-icon>
                            <span>Normal</span>
                        </v-btn>
                        <v-btn color="secondary" class="fill">
                            <v-icon>mdi-compare</v-icon>
                            <span>Threshold</span>
                        </v-btn>
                    </v-btn-toggle>
                </v-col>
            </v-row>
          </v-card>
        </v-col>
      </v-row>
      <v-row>

      </v-row>
      <v-row>

      </v-row>
    </v-container>
<!--    <camera-and-pipeline-select />-->
<!--    <v-row no-gutters>-->
<!--      &lt;!&ndash; vision tabs &ndash;&gt;-->
<!--      <v-col-->
<!--        cols="12"-->
<!--        sm="12"-->
<!--        md="7"-->
<!--        xl="6"-->
<!--        class="colsClass pr-8"-->
<!--      >-->
<!--        <v-tabs-->
<!--          v-if="($store.getters.currentPipelineIndex + 1) !== 0"-->
<!--          v-model="selectedTab"-->
<!--          fixed-tabs-->
<!--          :background-color="theme.background"-->
<!--          dark-->
<!--          height="48"-->
<!--          :slider-color="theme.accent"-->
<!--        >-->
<!--          <v-tab>Input</v-tab>-->
<!--          <v-tab>Threshold</v-tab>-->
<!--          <v-tab>Contours</v-tab>-->
<!--          <v-tab>Output</v-tab>-->
<!--          <v-tab>3D</v-tab>-->
<!--        </v-tabs>-->
<!--        <div-->
<!--          v-else-->
<!--          style="height: 48px"-->
<!--        />-->
<!--        <div style="padding-left:30px">-->
<!--          <keep-alive>-->
<!--            &lt;!&ndash; vision component &ndash;&gt;-->
<!--            <component-->
<!--              :is="selectedComponent"-->
<!--              ref="component"-->
<!--              v-model="$store.getters.pipeline"-->
<!--              @update="$emit('save')"-->
<!--            />-->
<!--          </keep-alive>-->
<!--        </div>-->
<!--      </v-col>-->
<!--      <v-col-->
<!--        cols="12"-->
<!--        sm="12"-->
<!--        md="5"-->
<!--        xl="6"-->
<!--        class="colsClass"-->
<!--      >-->
<!--        <div>-->
<!--          &lt;!&ndash; camera image tabs &ndash;&gt;-->
<!--          <v-tabs-->
<!--            v-if="($store.getters.currentPipelineIndex + 1) !== 0"-->
<!--            v-model="outputShowThresholded"-->
<!--            :background-color="theme.background"-->
<!--            dark-->
<!--            height="48"-->
<!--            :slider-color="theme.accent"-->
<!--            centered-->
<!--            style="padding-bottom:10px"-->
<!--            @change="handleTruthyPipelineData('outputShowThresholded')"-->
<!--          >-->
<!--            <v-tab>Normal</v-tab>-->
<!--            <v-tab>Threshold</v-tab>-->
<!--          </v-tabs>-->
<!--          <div-->
<!--            v-else-->
<!--            style="height: 58px"-->
<!--          />-->
<!--          &lt;!&ndash; camera image stream &ndash;&gt;-->
<!--          <div class="videoClass">-->
<!--            <v-row align="center">-->
<!--              <div style="position: relative; width: 100%; height: 100%;">-->
<!--                <cvImage-->
<!--                  :address="$store.getters.streamAddress"-->
<!--                  :scale="75"-->
<!--                  @click="onImageClick"-->
<!--                />-->
<!--                <span style=" position: absolute; top: 0.2%; left: 13%;">FPS:{{ parseFloat(fps).toFixed(2) }}</span>-->
<!--              </div>-->
<!--            </v-row>-->

<!--            <v-row align="center">-->
<!--              <v-simple-table-->
<!--                style="text-align: center;background-color: transparent; display: block;margin: auto"-->
<!--                dense-->
<!--                dark-->
<!--              >-->
<!--                <template v-slot:default>-->
<!--                  <thead>-->
<!--                    <tr>-->
<!--                      <th class="text-center">-->
<!--                        Target-->
<!--                      </th>-->
<!--                      <th class="text-center">-->
<!--                        Pitch-->
<!--                      </th>-->
<!--                      <th class="text-center">-->
<!--                        Yaw-->
<!--                      </th>-->
<!--                      <th class="text-center">-->
<!--                        Area-->
<!--                      </th>-->
<!--                    </tr>-->
<!--                  </thead>-->
<!--                  <tbody>-->
<!--                    <tr-->
<!--                      v-for="(value, index) in $store.getters.currentPipelineResults.targets"-->
<!--                      :key="index"-->
<!--                    >-->
<!--                      <td>{{ index }}</td>-->
<!--                      <td>{{ parseFloat(value['pitch']).toFixed(2) }}</td>-->
<!--                      <td>{{ parseFloat(value['yaw']).toFixed(2) }}</td>-->
<!--                      <td>{{ parseFloat(value['area']).toFixed(2) }}</td>-->
<!--                    </tr>-->
<!--                  </tbody>-->
<!--                </template>-->
<!--              </v-simple-table>-->
<!--            </v-row>-->
<!--          </div>-->
<!--        </div>-->
<!--      </v-col>-->
<!--    </v-row>-->
    <!-- snack bar -->
    <v-snackbar
      v-model="snackbar"
      :timeout="3000"
      top
      color="error"
    >
      <span style="color:#000">Can not remove the only pipeline!</span>
      <v-btn
        color="black"
        text
        @click="snackbar = false"
      >
        Close
      </v-btn>
    </v-snackbar>
  </div>
</template>

<script>
    import CameraAndPipelineSelect from "../components/pipeline/CameraAndPipelineSelect";
    import cvImage from '../components/common/cv-image'
    // import InputTab from './PipelineViews/InputTab'
    // import ThresholdTab from './PipelineViews/ThresholdTab'
    // import ContoursTab from './PipelineViews/ContoursTab'
    // import OutputTab from './PipelineViews/OutputTab'
    // import pnpTab from './PipelineViews/3D'

    export default {
        name: 'CameraTab',
        components: {
            CameraAndPipelineSelect,
            cvImage,
            // InputTab,
            // ThresholdTab,
            // ContoursTab,
            // OutputTab,
            // pnpTab,
        },
        data() {
            return {
                selectedTab: 0,
                snackbar: false,
            }
        },
        computed: {
            selectedOutputs: {
                get() {
                    // We switch the selector to single-select only on sm-and-down size devices, so we have to return a Number instead of an Array in that state
                    if (this.$vuetify.breakpoint.mdAndUp) {
                        return this.$store.getters.currentPipelineSettings.selectedOutputs;
                    } else {
                        return this.$store.getters.currentPipelineSettings.selectedOutputs[0] || 0;
                    }
                },
                set(value) {
                    let valToCommit = [0];
                    if (value instanceof Array) {
                        // Value is already an array, we don't need to do anything
                        valToCommit = value;
                    } else if (value) {
                        // Value is assumed to be a number, so we wrap it into an array
                        valToCommit = [value];
                    }
                    this.$store.commit('mutatePipeline', {'selectedOutputs': valToCommit});
                }
            },
            selectedComponent: {
                get() {
                    return (this.$store.getters.currentPipelineIndex + 1) === 0 ? "InputTab" : ["InputTab", "ThresholdTab", "ContoursTab", "OutputTab", "pnpTab"][this.selectedTab];
                }
            },
            fps: {
                get() {
                    return this.$store.getters.currentCameraFPS;
                }
            },
            // streams: {
            //   get() {
            //
            //   }
            // }
        },
        methods: {
            onImageClick(event) {
                if (this.selectedTab === 1) {
                    this.$refs.component.onClick(event);
                }
            },
        }
    }
</script>

<style scoped>
    .v-btn-toggle.fill {
        width: 100%;
    }

    .v-btn-toggle.fill > .v-btn {
        width: 50%;
    }

    .colsClass {
        padding: 0 !important;
    }

    .videoClass {
        text-align: center;
    }

    th {
        width: 80px;
        text-align: center;
    }
</style>