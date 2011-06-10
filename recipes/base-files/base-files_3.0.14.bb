DESCRIPTION = "Miscellaneous files for the base system."
SECTION = "base"
PRIORITY = "required"
PR = "r104"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://licenses/GPL-2;md5=94d55d512a9ba36caa9b7df079bae19f"

SRC_URI = " \
	   file://fstab \
           file://nsswitch.conf \
           file://licenses/BSD \
           file://licenses/GPL-2 \
           file://licenses/GPL-3 \
           file://licenses/LGPL-2 \
           file://licenses/LGPL-2.1 \
           file://licenses/LGPL-3 \
           file://licenses/GFDL-1.2 \
           file://licenses/Artistic "
S = "${WORKDIR}"

docdir_append = "/${P}"
dirs755 = "/dev /proc /sys ${sysconfdir}"
dirs2775 = ""
dirs1777 = "/tmp"

volatiles = "cache run log lock tmp"
conffiles = ""

#
# set standard hostname, might be a candidate for a DISTRO variable? :M:
#
hostname = "oe-micro"

do_install () {
	for d in ${dirs755}; do
		install -m 0755 -d "${D}$d"
	done
	for d in ${dirs1777}; do
		install -m 1777 -d "${D}$d"
	done
	for d in ${dirs2775}; do
		install -m 2755 -d "${D}$d"
	done
	for d in ${volatiles}; do
                if [ -d "${D}${localstatedir}/volatile/$d" ]; then
                        ln -sf volatile/$d "${D}/${localstatedir}/$d"
                fi
	done

	echo ${hostname} > "${D}${sysconfdir}/hostname"

	install -m 0644 "${WORKDIR}/fstab" "${D}${sysconfdir}/fstab"
	install -m 0644 "${WORKDIR}/nsswitch.conf" "${D}${sysconfdir}/nsswitch.conf"
}

PACKAGES = "${PN}-dbg ${PN}-doc ${PN}"
FILES_${PN} = "/*"
FILES_${PN}-doc = "${docdir} ${datadir}/common-licenses"

PACKAGE_ARCH = "${MACHINE_ARCH}"
