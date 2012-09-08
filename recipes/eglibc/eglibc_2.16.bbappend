PR .= ".micro2"

THISDIR := "${@os.path.dirname(bb.data.getVar('FILE', d, True))}"
FILESPATH =. "${@base_set_filespath(["${THISDIR}/${PN}-${PV}", "${THISDIR}/files"], d)}:"

SRC_URI += "file://ldso-no-hwcaps.patch"

PACKAGES = "${PN}-dbg catchsegv sln nscd ldd ${PN}-mtrace ${PN}-utils eglibc-thread-db ${PN}-pic libcidn libmemusage libsegfault ${PN}-pcprofile libsotruss ${PN} eglibc-extra-nss ${PN}-dev ${PN}-staticdev ${PN}-doc" 

FILES_sln = "${sbindir}/sln"

do_install_append() {
	if [ "${sbindir}" != "/sbin" ]; then
		mv ${D}/sbin/sln ${D}${sbindir}/
	fi
	rm ${D}${sysconfdir}/ld.so.conf
	rm ${D}/sbin/ldconfig
	rmdir ${D}${sysconfdir} || true
	rmdir ${D}/sbin || true
}
